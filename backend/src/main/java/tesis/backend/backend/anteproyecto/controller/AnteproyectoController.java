package tesis.backend.backend.anteproyecto.controller;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tesis.backend.backend.anteproyecto.entity.Anteproyecto;
import tesis.backend.backend.anteproyecto.service.AnteproyectoService;

@RestController
@RequestMapping("/api/v1/anteproyecto")
public class AnteproyectoController {

    @Autowired
    private AnteproyectoService anteproyectoService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Anteproyecto> getAllAnteproyectos() {
        return anteproyectoService.getAllAnteproyectos();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addAnteproyecto(@RequestBody() Anteproyecto anteproyecto) {
        return anteproyectoService.addAnteproyecto(anteproyecto);
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteAnteproyecto(@PathVariable("id") UUID id) {
        return anteproyectoService.deleteAnteproyecto(id);
    }

    // AUTOR
    @GetMapping("/add/autor/{idAutor}/{idanteproyecto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addAutorToAnteproyecto(@PathVariable("idAutor") UUID idAutor, @PathVariable("idanteproyecto") UUID idAnteproyecto) {
        return anteproyectoService.addAutorToAnteproyecto(idAutor, idAnteproyecto);
    }
    
    @DeleteMapping("/delete/autor/{idAutor}/{idanteproyecto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteAutor(@PathVariable("idAutor") UUID idAutor, @PathVariable("idanteproyecto") UUID idAnteproyecto) {
        return anteproyectoService.deleteAutor(idAutor, idAnteproyecto);
    }
    
    // EVALUADOR
    @GetMapping("/add/evaluador/{idEvaluador}/{idanteproyecto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addEvaluadorToAnteproyecto(@PathVariable("idEvaluador") UUID idEvaluador, @PathVariable("idanteproyecto") UUID idAnteproyecto) {
        return anteproyectoService.addEvaluadorToAnteproyecto(idEvaluador, idAnteproyecto);
    }

    @DeleteMapping("/delete/evaluador/{idEvaluador}/{idanteproyecto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteEvaluador(@PathVariable("idEvaluador") UUID idEvaluador, @PathVariable("idanteproyecto") UUID idAnteproyecto) {
        return anteproyectoService.deleteEvaluador(idEvaluador, idAnteproyecto);
    }
    
    // FECHA
    @PutMapping("/fecha/entrega/{id}/{fecha}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addFechaEntrega(@PathVariable("id") UUID id, @PathVariable("fecha") Date date) {
        return anteproyectoService.addFechaEntrega(id, date);
    }
    
    @PutMapping("/fecha/devolucion/{id}/{fecha}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addFechaDevolucion(@PathVariable("id") UUID id, @PathVariable("fecha") Date date) {
        return anteproyectoService.addFechaDevolucion(id, date);
    }

    // ESTADO
    @PutMapping("/estado/{estado}/{idAnte}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> changeEstado(@PathVariable("estado") Integer estado, @PathVariable("idAnte") UUID id) {
        return anteproyectoService.changeEstado(estado, id);
    }
    
}
