package tesis.backend.backend.anteproyecto.entity;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import tesis.backend.backend.user.entity.User;

@Entity
@Table(name = "anteproyecto", schema = "public")
@Data
@NoArgsConstructor
public class Anteproyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "anteproyectoid", updatable = false)
    private UUID anteproyectoId;

    @Column(name = "noradicacion")
    private Integer noRadicacion;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "fechaentregaaevaluador")
    private Date fechaEntregaAEvaluador;
    
    @Column(name = "fechaentregadeevaluador")
    private Date fechaEntregaDeEvaluador;

    @Column(name = "estado")
    private Integer estado;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "autores",
            joinColumns = @JoinColumn(name = "anteproyectoid"),
            inverseJoinColumns = @JoinColumn(name = "userid")
    )
    private Set<User> autores;

    @ManyToMany
    @JoinTable(
            name = "evaluadores",
            joinColumns = @JoinColumn(name = "anteproyectoid"),
            inverseJoinColumns = @JoinColumn(name = "userid")
    )
    private Set<User> evaluadores;

    public Anteproyecto(Integer noRadicacion, String titulo, Date fechaEntregaAEvaluador, Date fechaEntregaDeEvaluador,
            Integer estado) {
        this.noRadicacion = noRadicacion;
        this.titulo = titulo;
        this.fechaEntregaAEvaluador = fechaEntregaAEvaluador;
        this.fechaEntregaDeEvaluador = fechaEntregaDeEvaluador;
        this.estado = estado;
    }
    
    public Anteproyecto(Integer noRadicacion, String titulo, Integer estado) {
        this.noRadicacion = noRadicacion;
        this.titulo = titulo;
        this.estado = estado;
    }

}