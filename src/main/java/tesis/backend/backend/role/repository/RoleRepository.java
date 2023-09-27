package tesis.backend.backend.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tesis.backend.backend.role.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
