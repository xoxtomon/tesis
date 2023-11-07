package tesis.backend.backend.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tesis.backend.backend.evaluador.entity.Evaluador;
import tesis.backend.backend.role.entity.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "user", schema = "public")
@Data
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userid", updatable = false)
    private UUID userId;

    @Column(name = "personalid", updatable = false)
    private int personalId;

    @Column(name = "email")
    @JsonProperty("email") // to make requests with key "email" without changing backend logic
    private String username;

    @Column(name = "passwordhash")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "userroles",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "roleid")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "userId")
    private Set<Evaluador> evaluador;

    public User(int personalId, String username, String password, String name, String lastname, Boolean estudiante, Set<Role> roles) {
        this.personalId = personalId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // GET the roles
        Set<Role> roles = getRoles();
        // Create a new collection to hold the GrantedAuthority objects
        Set<GrantedAuthority> authorities = new HashSet<>();

        for(Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getDescripcion()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
