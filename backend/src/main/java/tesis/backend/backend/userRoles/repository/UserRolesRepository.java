package tesis.backend.backend.userRoles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tesis.backend.backend.userRoles.entity.UserRoles;

import java.util.UUID;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, UUID> {

}
