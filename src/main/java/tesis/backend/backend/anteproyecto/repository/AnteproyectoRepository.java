package tesis.backend.backend.anteproyecto.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tesis.backend.backend.anteproyecto.entity.Anteproyecto;

@Repository
public interface AnteproyectoRepository extends JpaRepository<Anteproyecto, UUID> {

    @Query("Select a FROM Anteproyecto a where a.noRadicacion = ?1")
    Optional<Anteproyecto> findByNoRadicacion(Integer noRadicacion);

    @Query("SELECT a from Anteproyecto a JOIN a.autores u WHERE u.userId = ?1")
    Optional<Anteproyecto> findByAutorId(UUID idAutor);
    
}
