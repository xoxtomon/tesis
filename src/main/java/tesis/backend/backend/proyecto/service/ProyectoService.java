package tesis.backend.backend.proyecto.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tesis.backend.backend.anteproyecto.entity.Anteproyecto;
import tesis.backend.backend.anteproyecto.repository.AnteproyectoRepository;
import tesis.backend.backend.proyecto.entity.Proyecto;
import tesis.backend.backend.proyecto.repository.ProyectoRepository;

@Service
public class ProyectoService {
    @Autowired
    private ProyectoRepository proyectoRepository;
    @Autowired
    private AnteproyectoRepository anteproyectoRepository;

    public ResponseEntity<String> addProyecto(Proyecto proyecto) {
        // VALIDAR QUE EL ID ESTE LIGADO A UN ANTEPROYECTO
        Optional<Anteproyecto> optionalAnte = anteproyectoRepository.findById(proyecto.getAnteproyectoId());
        if (!optionalAnte.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el Id del anteproyecto");
        }

        // GUARDAR EL PROYECTO
        try {
            proyectoRepository.save(proyecto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Proyecto creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Proyecto no pudo ser creado.");
        }
    }

    public List<Proyecto> getProyectos() {
        return proyectoRepository.findAll();
    }

    public ResponseEntity<String> setFechaSustentacion(UUID id, Date date) {
        // VALIDAR QUE EL ID ESTE LIGADO A UN ANTEPROYECTO
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);
        if (!optionalProyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el proyecto.");
        }

        try {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setFechaSustentacion(date);
            proyectoRepository.save(proyecto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Fecha cambiada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar la fecha.");
        }
    }

    public ResponseEntity<String> setNotaDefinitiva(UUID id, Float nota) {
        // VALIDAR QUE EL ID ESTE LIGADO A UN ANTEPROYECTO
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);
        if (!optionalProyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el proyecto.");
        }

        if (nota > 5 || nota < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La nota proporcionada no está en el rango adecuado.");
        }

        try {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setNotaDefinitiva(nota);
            proyectoRepository.save(proyecto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Nota definitva cambiada exitosamente.");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar la nota.");
        }
    }

    public ResponseEntity<String> setNroActa(UUID id, Integer acta) {
        // VALIDAR QUE EL ID ESTE LIGADO A UN ANTEPROYECTO
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);
        if (!optionalProyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el proyecto.");
        }

        try {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setNroActa(acta);
            proyectoRepository.save(proyecto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Número de acta cambiado exitosamente.");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar el número de acta.");
        }
    }

    public ResponseEntity<String> setMencionHonor(UUID id, Boolean mencion) {
        // VALIDAR QUE EL ID ESTE LIGADO A UN ANTEPROYECTO
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);
        if (!optionalProyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el proyecto.");
        }

        try {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setMencionHonor(mencion);
            proyectoRepository.save(proyecto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Mención de Honor modificada exitosamente.");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo modificar el estado de mención de honor.");
        }
    }

    public ResponseEntity<String> setPostulacion(UUID id, Date date) {
        // VALIDAR QUE EL ID ESTE LIGADO A UN ANTEPROYECTO
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);
        if (!optionalProyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el proyecto.");
        }

        try {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setGradoPostulacion(date);
            proyectoRepository.save(proyecto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Fecha de postulación cambiada exitosamente.");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar la fecha de postulación.");
        }
    }

    public ResponseEntity<String> setEntregaDocs(UUID id, Boolean entregas) {
        // VALIDAR QUE EL ID ESTE LIGADO A UN ANTEPROYECTO
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);
        if (!optionalProyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el proyecto.");
        }

        try {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setEntregaDocs(entregas);
            proyectoRepository.save(proyecto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Entrega de documentos cambiada exitosamente.");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar la entrega de documentos.");
        }
    }

    public ResponseEntity<String> setFechaCreacion(UUID id, Date date) {
        // VALIDAR QUE EL ID ESTE LIGADO A UN ANTEPROYECTO
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);
        if (!optionalProyecto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el proyecto.");
        }

        try {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setFechaCreacion(date);
            proyectoRepository.save(proyecto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Fecha de Creación cambiada exitosamente.");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar la fecha de creación.");
        }
    }
}
