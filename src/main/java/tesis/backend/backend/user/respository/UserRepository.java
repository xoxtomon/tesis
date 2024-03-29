package tesis.backend.backend.user.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tesis.backend.backend.user.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.personalId = ?1")
    Optional<User> findByPersonalId(int personalId);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("SELECT u.userId FROM User u WHERE u.username = ?1")
    Optional<UUID> findIdByEmail(String email);
}
