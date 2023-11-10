package tesis.backend.backend.anteproyecto.controller;

import java.sql.Date;
import java.util.List;
import java.util.Set;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tesis.backend.backend.anteproyecto.entity.Anteproyecto;
import tesis.backend.backend.anteproyecto.entity.AnteproyectoInput;
import tesis.backend.backend.anteproyecto.service.AnteproyectoService;
import tesis.backend.backend.user.entity.User;

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

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addAnteproyecto(@RequestBody() AnteproyectoInput anteproyectoInput) {
        return anteproyectoService.addAnteproyecto(anteproyectoInput);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteAnteproyecto(@PathVariable("id") UUID id) {
        return anteproyectoService.deleteAnteproyecto(id);
    }

    // AUTOR
    @GetMapping("/autor/{idanteproyecto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Set<User> findAutoresOfAnteproyecto(@PathVariable("idanteproyecto") UUID idAnteproyecto) {
        return anteproyectoService.findAutoresOfAnteproyecto(idAnteproyecto);
    }

    @PostMapping("/autor/{idAutor}/{idanteproyecto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addAutorToAnteproyecto(@PathVariable("idAutor") UUID idAutor,
            @PathVariable("idanteproyecto") UUID idAnteproyecto) {
        return anteproyectoService.addAutorToAnteproyecto(idAutor, idAnteproyecto);
    }

    @DeleteMapping("/autor/{idAutor}/{idanteproyecto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteAutor(@PathVariable("idAutor") UUID idAutor,
            @PathVariable("idanteproyecto") UUID idAnteproyecto) {
        return anteproyectoService.deleteAutor(idAutor, idAnteproyecto);
    }

    // EVALUADOR
    @PostMapping("/evaluador/{idEvaluador}/{idanteproyecto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addEvaluadorToAnteproyecto(@PathVariable("idEvaluador") UUID idEvaluador,
            @PathVariable("idanteproyecto") UUID idAnteproyecto, @RequestParam Boolean isDirector) {
        return anteproyectoService.addEvaluadorToAnteproyecto(idEvaluador, idAnteproyecto, isDirector);
    }

    @DeleteMapping("/evaluador/{idEvaluador}/{idAnteproyecto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteEvaluador(@PathVariable("idEvaluador") UUID idEvaluador,
            @PathVariable("idAnteproyecto") UUID idAnteproyecto) {
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

    // TODO add put method for fechacreacion
    @PutMapping("/fecha/creacion/{id}/{fecha}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addFechaCreacion(@PathVariable("id") UUID id, @PathVariable("fecha") Date date) {
        return anteproyectoService.addFechaCreacion(id, date);
    }

    // ESTADO
    @PutMapping("/estado/{estado}/{idAnte}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> changeEstado(@PathVariable("estado") Integer estado,
            @PathVariable("idAnte") UUID id) {
        return anteproyectoService.changeEstado(estado, id);
    }

}
