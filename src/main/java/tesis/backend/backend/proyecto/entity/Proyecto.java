package tesis.backend.backend.proyecto.entity;

import java.sql.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proyectogrado", schema = "public")
@Data
@NoArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "proyectoid", updatable = false)
    private UUID proyectoId;
    
    @Column(name = "anteproyectoid", updatable = false)
    private UUID anteproyectoId;
    
    @Column(name = "fechasustentacion")
    private Date fechaSustentacion;
    
    @Column(name = "fechacreacion")
    private Date fechaCreacion;
    
    @Column(name = "notadefinitiva")
    private Float notaDefinitiva;
    
    @Column(name = "nroacta")
    private Integer nroActa;
   
    @Column(name = "mencionhonor")
    private Boolean mencionHonor;

    @Column(name = "gradopostulacion")
    private Date gradoPostulacion;
    
    @Column(name = "entregadocs")
    private Boolean entregaDocs;

    public Proyecto(UUID anteproyectoId) {
        this.anteproyectoId = anteproyectoId;
    }

    
}
