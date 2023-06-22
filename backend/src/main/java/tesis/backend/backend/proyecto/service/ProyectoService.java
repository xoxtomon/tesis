package tesis.backend.backend.proyecto.service;

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
        if(!optionalAnte.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe el Id del anteproyecto");
        }
        
        // GUARDAR EL PROYECTO
        try {
            proyectoRepository.save(proyecto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Proyecto creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Proyecto no pudo ser creado.");
        }
    }
}
