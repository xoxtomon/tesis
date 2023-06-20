package tesis.backend.backend.anteproyecto.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/add/autor/{idAutor}/{idanteproyecto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addAutorToAnteproyecto(@PathVariable("idAutor") UUID idAutor, @PathVariable("idanteproyectos") UUID idAnteproyecto) {
        return anteproyectoService.addAutorToAnteproyecto(idAutor, idAnteproyecto);
    }
    
}
