package tesis.backend.backend.anteproyecto.service;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tesis.backend.backend.anteproyecto.entity.Anteproyecto;
import tesis.backend.backend.anteproyecto.entity.AnteproyectoInput;
import tesis.backend.backend.anteproyecto.entity.AnteproyectoInput.EvaluadorInfo;
import tesis.backend.backend.anteproyecto.repository.AnteproyectoRepository;
import tesis.backend.backend.evaluador.entity.Evaluador;
import tesis.backend.backend.evaluador.repository.EvaluadorRepository;
import tesis.backend.backend.role.entity.Role;
import tesis.backend.backend.user.entity.User;
import tesis.backend.backend.user.respository.UserRepository;

@Service
public class AnteproyectoService {
    @Autowired
    private AnteproyectoRepository anteproyectoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EvaluadorRepository evaluadorRepository;

    public List<Anteproyecto> getAllAnteproyectos() {
        return anteproyectoRepository.findAll();
    }

    public ResponseEntity<?> addAnteproyecto(AnteproyectoInput anteproyectoInput) {

        // CHECK if nroRadicacion is already used
        Optional<Anteproyecto> OptionalAnteproyecto = anteproyectoRepository
                .findByNoRadicacion(anteproyectoInput.getNroRadicacion());
        if (OptionalAnteproyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Radicacion ya usado.");
        }
        
        //Create Anteproyecto Object
        Anteproyecto anteproyecto = new Anteproyecto(anteproyectoInput.getNroRadicacion(),anteproyectoInput.getTitulo());
        anteproyectoRepository.save(anteproyecto);
        
        // Add every autor to the object
        for (String emailAutor : anteproyectoInput.getAutores()) {
            try {
                UUID userUuid = userRepository.findIdByEmail(emailAutor).get();
                addAutorToAnteproyecto(userUuid, anteproyecto.getAnteproyectoId());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el anteproyecto con el autor: " + emailAutor);
            }
        }
        
        // Add every evaluador to the object
        for (EvaluadorInfo evaluador : anteproyectoInput.getEvaluadores()) {
            String email = evaluador.getEmail();
            try {
                UUID userUuid = userRepository.findIdByEmail(email).get();
                addEvaluadorToAnteproyecto(userUuid, anteproyecto.getAnteproyectoId(), evaluador.isDirector());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el anteproyecto con el evaluador"+ email);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Anteproyecto creado satisfactoriamente.");
    }

    // Delete all autores, delete all evaluadores, then delete anteproyecto
    public ResponseEntity<String> deleteAnteproyecto(UUID id) {
        try {
            anteproyectoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anteproyecto borrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Anteproyecto no pudo ser borrado");
        }
    }

    public ResponseEntity<String> addAutorToAnteproyecto(UUID idAutor, UUID idAnteproyecto) {
        // Busco un anteproyecto por el uuid de usuario, si encuentro no puedo asociarlo
        // Pues el autor ya tiene un anteproyecto asociado.
        Optional<Anteproyecto> optionalAnteproyectoByAutor = anteproyectoRepository.findByAutorId(idAutor);
        if (optionalAnteproyectoByAutor.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El autor ya tiene un proyecto asociado.");
        }

        // Traer el anteproyecto y ingresar a su nuevo autor.
        Optional<Anteproyecto> optionalAnteproyecto = anteproyectoRepository.findById(idAnteproyecto);
        if (optionalAnteproyecto.isPresent()) {

            Optional<User> optionalAutor = userRepository.findById(idAutor);
            if (optionalAutor.isPresent()) {
                // Obtener El anteproyecto y los usuarios de ese anteproyecto
                Anteproyecto anteproyecto = optionalAnteproyecto.get();
                Set<User> autores = anteproyecto.getAutores();
                // Obtener el usuario del uuid y agregarlo al Set
                User autor = optionalAutor.get();

                // Si Tiene más de un Rol o el Rol que tiene no es de estudiante no debe poder
                // agregarse como Autor
                if (!hasOneRole(autor)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El Usuario tiene más de un rol");
                }
                if (!hasRoleStudent(autor)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("El Usuario no tiene el rol de ESTUDIANTE.");
                }

                autores.add(autor);
                anteproyecto.setAutores(autores);

                anteproyectoRepository.save(anteproyecto);

                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Se agrego al autor satisfactoriamente");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fallo al adicionar autor");
    }

    public ResponseEntity<String> deleteAutor(UUID idAutor, UUID idanteproyecto) {
        Optional<Anteproyecto> optionalAnteproyecto = anteproyectoRepository.findById(idanteproyecto);
        Optional<User> optionalUser = userRepository.findById(idAutor);
        // Validar que el Anteproyecto y el usuario existan
        if (!optionalAnteproyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Anteproyecto no encontrado");
        }
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado");
        }
        Anteproyecto anteproyecto = optionalAnteproyecto.get();
        User usuario = optionalUser.get();
        // Validar que el usuario es autor del anteproyecto
        Set<User> autores = anteproyecto.getAutores();
        if (!autores.contains(usuario)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no es autor del anteproyecto");
        }
        // Borrar y guardar el nuevo conjunto de usuarios en el anteproyecto
        // Guardar el nuevo Anteproyecto en el repositorio
        autores.remove(usuario);
        anteproyecto.setAutores(autores);
        anteproyectoRepository.save(anteproyecto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Autor borrado existosamente");
    }

    public ResponseEntity<String> addEvaluadorToAnteproyecto(UUID idEvaluador, UUID idAnteproyecto, Boolean isDirector) {
        // VALIDACION DE EXISTENCIA DE ANTEPROYECTO Y USUARIO
        Optional<Anteproyecto> optionalAnteproyecto = anteproyectoRepository.findById(idAnteproyecto);
        if (!optionalAnteproyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Anteproyecto no encontrado.");
        }
        Optional<User> optionalEvaluador = userRepository.findById(idEvaluador);
        Anteproyecto ante = optionalAnteproyecto.get();
        if (!optionalEvaluador.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Evaluador no encontrado.");
        }

        // VALIDACIONES DE ROLES DE USUARIO
        User evaluador = optionalEvaluador.get();
        if (!hasRoleEvaluador(evaluador)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no tiene el rol evaluador");
        }
        if (isDirector && hasDirector(ante)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El Anteproyecto ya tiene Director asignado.");
        }
        if (hasRoleStudent(evaluador)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Los evaluadores no pueden tener el rol de ESTUDIANTE");
        }

        Evaluador evaluadorRelation = new Evaluador(idAnteproyecto, idEvaluador, isDirector);
        evaluadorRepository.save(evaluadorRelation);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Evaluador agregado existosamente.");
    }

    public ResponseEntity<String> deleteEvaluador(UUID idEvaluador, UUID idAnteproyecto) {
        // VALIDAR EXISTENCIA DEL ANTEPROYECTO Y EVALUADOR
        Optional<Anteproyecto> optionalAnteproyecto = anteproyectoRepository.findById(idAnteproyecto);
        if (!optionalAnteproyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró el anteproyecto.");
        }
        Optional<User> optionalUser = userRepository.findById(idEvaluador);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró el usuario.");
        }
        Anteproyecto ante = optionalAnteproyecto.get();
        //User evaluador = optionalUser.get();
        
        // VALIDAR QUE EL USUARIO SEA EVALUADOR DEL ANTEPROYECTO
        Set<Evaluador> evaluadores = ante.getEvaluadores();
        for (Evaluador evaluador : evaluadores) {
            if (idEvaluador.equals(evaluador.getUserId())) {
                evaluadorRepository.delete(evaluador);
                return ResponseEntity.status(HttpStatus.OK).body("El evaluador fue eliminado del anteproyecto.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo realizar la accion.");
    }

    public ResponseEntity<String> addFechaEntrega(UUID id, Date date) {
        Optional<Anteproyecto> optionalAnteproyecto = anteproyectoRepository.findById(id);
        if (!optionalAnteproyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El anteproyecto no existe.");
        }
        Anteproyecto ante = optionalAnteproyecto.get();
        ante.setFechaEntregaAEvaluador(date);
        anteproyectoRepository.save(ante);

        return ResponseEntity.status(HttpStatus.OK).body("Fecha de entrega agregada existosamente.");
    }

    public ResponseEntity<String> addFechaDevolucion(UUID id, Date date) {
        Optional<Anteproyecto> optionalAnteproyecto = anteproyectoRepository.findById(id);
        if (!optionalAnteproyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El anteproyecto no existe.");
        }
        Anteproyecto ante = optionalAnteproyecto.get();
        ante.setFechaEntregaDeEvaluador(date);
        anteproyectoRepository.save(ante);

        return ResponseEntity.status(HttpStatus.OK).body("Fecha de devolución agregada existosamente.");
    }

    public ResponseEntity<String> addFechaCreacion(UUID id, Date date) {
        Optional<Anteproyecto> optionalAnteproyecto = anteproyectoRepository.findById(id);
        if (!optionalAnteproyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El anteproyecto no existe.");
        }
        Anteproyecto ante = optionalAnteproyecto.get();
        ante.setFechaCreacion(date);
        anteproyectoRepository.save(ante);

        return ResponseEntity.status(HttpStatus.OK).body("Fecha de creacion agregada existosamente.");
    }

    public ResponseEntity<String> changeEstado(Integer estado, UUID id) {
        Optional<Anteproyecto> optionalAnteproyecto = anteproyectoRepository.findById(id);
        if (!optionalAnteproyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Anteproyecto no encontrado.");
        }

        Anteproyecto ante = optionalAnteproyecto.get();
        try {
            ante.setEstado(estado);
            anteproyectoRepository.save(ante);
            return ResponseEntity.status(HttpStatus.OK).body("Estado cambiado satisfactoriamente.");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El estado enviado no existe.");
        }
    }

    // UTIL
    // CHECK
    private Boolean hasRoleStudent(User user) {
        Iterator<Role> itr = user.getRoles().iterator();
        while (itr.hasNext()) {
            String descripcion = itr.next().getDescripcion();
            if (descripcion.equals("ESTUDIANTE")) {
                return true;
            }
        }
        return false;
    }

    private Boolean hasRoleEvaluador(User user) {
        Iterator<Role> itr = user.getRoles().iterator();
        while (itr.hasNext()) {
            String descripcion = itr.next().getDescripcion();
            if (descripcion.equals("EVALUADOR")) {
                return true;
            }
        }
        return false;
    }

    private Boolean hasOneRole(User user) {
        if (user.getRoles().size() != 1) {
            return false;
        }
        return true;
    }

    private boolean hasDirector(Anteproyecto ante) {
        Set<Evaluador> evaluadores = ante.getEvaluadores();
        for (Evaluador evaluador : evaluadores) {
            if (evaluador.getDirector()) {
                return true;
            }
        }
        return false;
    }
}
