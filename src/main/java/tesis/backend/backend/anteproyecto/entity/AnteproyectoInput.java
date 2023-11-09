package tesis.backend.backend.anteproyecto.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnteproyectoInput {
    
    private Integer nroRadicacion;
    private String titulo;
    private List<String> autores;
    private List<String> evaluadores;

    public AnteproyectoInput(Integer nroRadicacion, String titulo, List<String> autores, List<String> evaluadores) {
        this.nroRadicacion = nroRadicacion;
        this.titulo = titulo;
        this.autores = autores;
        this.evaluadores = evaluadores;
    }
}
