package tesis.backend.backend.role.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "role", schema = "public")
@NoArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleid")
    private Integer roleId;

    @Column(name = "descripcion")
    private String descripcion;

    public Role(Integer roleId, String descripcion) {
        this.roleId = roleId;
        this.descripcion = descripcion;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
