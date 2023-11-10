package tesis.backend.backend.anteproyecto.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnteproyectoInput {
    private int nroRadicacion;
    private String titulo;
    private List<String> autores;
    private List<EvaluadorInfo> evaluadores;

    public AnteproyectoInput() {}
    public AnteproyectoInput(int nroRadicacion, String titulo, List<String> autores, List<EvaluadorInfo> evaluadores) {
        this.nroRadicacion = nroRadicacion;
        this.titulo = titulo;
        this.autores = autores;
        this.evaluadores = evaluadores;
    }

    public static class EvaluadorInfo {
        private String email;
        private boolean isDirector;

        // Constructors, getters, and setters...

        // No-args constructor required for Jackson deserialization
        public EvaluadorInfo() {}

        public EvaluadorInfo(String email, boolean isDirector) {
            this.email = email;
            this.isDirector = isDirector;
        }

        public String getEmail() {
            return this.email;
        }
        public void setEmail(String newemail) {
            this.email = newemail;
        }
        
        public boolean getIsdirector() {
            return this.isDirector;
        }
        public void setEmail(Boolean isDirector) {
            this.isDirector = isDirector;
        }
    }
}
