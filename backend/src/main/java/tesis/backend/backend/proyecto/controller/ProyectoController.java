package tesis.backend.backend.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tesis.backend.backend.proyecto.entity.Proyecto;
import tesis.backend.backend.proyecto.service.ProyectoService;

@RestController
@RequestMapping("/api/v1/proyecto")
public class ProyectoController {
    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Proyecto> getProyectos() {
        return proyectoService.getProyectos();
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addProyecto(@RequestBody() Proyecto proyecto) {
        return proyectoService.addProyecto(proyecto);
    }
}
