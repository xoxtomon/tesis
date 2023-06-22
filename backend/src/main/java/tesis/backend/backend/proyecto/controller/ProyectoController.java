package tesis.backend.backend.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping()
    public ResponseEntity<String> addProyecto(@RequestBody() Proyecto proyecto) {
        System.out.println(proyecto);
        return proyectoService.addProyecto(proyecto);
    }
}
