package tesis.backend.backend.evaluador.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evaluadores", schema = "public")
@Data
@NoArgsConstructor
public class Evaluador {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "anteproyectoid")
    private UUID anteproyectoId;

    @Column(name = "userid")
    private UUID userId;

    @Column(name = "director")
    private Boolean director;

    public Evaluador(UUID anteproyectoId, UUID userId, Boolean director) {
        this.anteproyectoId = anteproyectoId;
        this.userId = userId;
        this.director = director;
    }
}
