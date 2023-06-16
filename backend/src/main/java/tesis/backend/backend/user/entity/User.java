package tesis.backend.backend.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user", schema = "public")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userid", updatable = false)
    private UUID userId;

    @Column(name = "personalid", updatable = false)
    private int personalId;

    @Column(name = "username")
    private String username;

    @Column(name = "passwordhash")
    private String passwordHash;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "estudiante", updatable = false)
    private Boolean estudiante;

    public User(int personalId, String username, String passwordHash, String name, String lastname, Boolean estudiante) {
        this.personalId = personalId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.lastname = lastname;
        this.estudiante = estudiante;
    }
}
