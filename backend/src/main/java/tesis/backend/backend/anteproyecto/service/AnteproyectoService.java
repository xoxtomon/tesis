package tesis.backend.backend.anteproyecto.service;

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
import tesis.backend.backend.anteproyecto.repository.AnteproyectoRepository;
import tesis.backend.backend.user.entity.User;
import tesis.backend.backend.user.respository.UserRepository;

@Service
public class AnteproyectoService {
    @Autowired
    private AnteproyectoRepository anteproyectoRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Anteproyecto> getAllAnteproyectos() {
        return anteproyectoRepository.findAll();
    }

    public ResponseEntity<?> addAnteproyecto(Anteproyecto anteproyecto) {
        Optional<Anteproyecto> OptionalAnteproyecto = anteproyectoRepository.findByNoRadicacion(anteproyecto.getNoRadicacion());
        if(OptionalAnteproyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Radicacion ya usado.");
        }
        Anteproyecto savedAnteproyecto = anteproyectoRepository.save(anteproyecto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnteproyecto);
    }

    public ResponseEntity<String> addAutorToAnteproyecto(UUID idAutor, UUID idAnteproyecto) {
        // Busco un anteproyecto por el uuid de usuario, si encuentro no puedo asociarlo
        // Pues el autor ya tiene un anteproyecto asociado.
        Optional<Anteproyecto> optionalAnteproyectoByAutor = anteproyectoRepository.findByAutorId(idAnteproyecto);
        if(optionalAnteproyectoByAutor.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El autor ya tiene un proyecto asociado.");
        }
        
        // Traer el anteproyecto y ingresar a su nuevo autor.
        Optional<Anteproyecto> optionalAnteproyecto = anteproyectoRepository.findById(idAnteproyecto);
        if (optionalAnteproyecto.isPresent()) {
            

            Optional<User> optionalAutor = userRepository.findById(idAutor);
            if(optionalAutor.isPresent()) {
                // Obtener El anteproyecto y los usuarios de ese anteproyecto
                Anteproyecto anteproyecto = optionalAnteproyecto.get();
                Set<User> autores = anteproyecto.getAutores();
                // Obtener el usuario del uuid y agregarlo al Set
                User autor = optionalAutor.get();
                autores.add(autor);
                anteproyecto.setAutores(autores);

                anteproyectoRepository.save(anteproyecto);

                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Se agrego al autor satisfactoriamente");
            }
        } 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fallo al adicionar autor");
    }
    

}
