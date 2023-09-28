package tesis.backend.backend.proyecto.controller;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/sustentacion/{id}/{date}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> setFechaSustentacion(@PathVariable("id") UUID id, @PathVariable("date") Date date) {
        return proyectoService.setFechaSustentacion(id, date);
    }

    @PutMapping("/definitiva/{id}/{nota}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> setFechaSustentacion(@PathVariable("id") UUID id, @PathVariable("nota") Float nota) {
        return proyectoService.setNotaDefinitiva(id, nota);
    }

    @PutMapping("/acta/{id}/{acta}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> setFechaSustentacion(@PathVariable("id") UUID id,
            @PathVariable("acta") Integer acta) {
        return proyectoService.setNroActa(id, acta);
    }

    @PutMapping("/mencion/{id}/{mencion}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> setMencionHonor(@PathVariable("id") UUID id,
            @PathVariable("mencion") Boolean mencion) {
        return proyectoService.setMencionHonor(id, mencion);
    }

    @PutMapping("/postulacion/{id}/{fecha}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> setPostulacion(@PathVariable("id") UUID id, @PathVariable("fecha") Date fecha) {
        return proyectoService.setPostulacion(id, fecha);
    }

    @PutMapping("/docs/{id}/{docs}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> setEntregaDocs(@PathVariable("id") UUID id, @PathVariable("docs") Boolean docs) {
        return proyectoService.setEntregaDocs(id, docs);
    }

    // TODO put method for fechacreacion
    @PutMapping("/fecha-creacion/{id}/{fecha}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> setFechaCreacion(@PathVariable("id") UUID id, @PathVariable("fecha") Date fecha) {
        return proyectoService.setFechaCreacion(id, fecha);
    }
}
