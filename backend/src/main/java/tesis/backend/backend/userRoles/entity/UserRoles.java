package tesis.backend.backend.userRoles.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "userroles", schema = "public")
@Data
@NoArgsConstructor
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Userroleid", updatable = false)
    private UUID userRoleId;

    @Column(name = "userid")
    private UUID userId; //PersonalID

    @Column(name = "roleid")
    private Integer roleId;

    public UserRoles(UUID userId, Integer roleId) {
        this.userRoleId = userRoleId;
        this.userId = userId;
        this.roleId = roleId;
    }
}
