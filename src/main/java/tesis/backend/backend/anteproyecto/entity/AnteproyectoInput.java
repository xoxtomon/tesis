package tesis.backend.backend.anteproyecto.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnteproyectoInput {
    private int nroRadicacion;
    private String titulo;
    private List<String> autores;
    private List<EvaluadorInfo> evaluadores;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvaluadorInfo {
        private String email;
        private boolean director;
    }
}
