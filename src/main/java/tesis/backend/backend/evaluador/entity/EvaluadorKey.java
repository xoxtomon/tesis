package tesis.backend.backend.evaluador.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tesis.backend.backend.anteproyecto.entity.Anteproyecto;
import tesis.backend.backend.user.entity.User;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EvaluadorKey implements Serializable {
    @Column(name = "userid")
    private UUID userId;
    
    @Column(name = "anteproyectoid")
    private UUID anteproyectoId;
}
