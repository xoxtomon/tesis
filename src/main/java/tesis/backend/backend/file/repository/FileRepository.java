package tesis.backend.backend.file.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tesis.backend.backend.file.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
    @Query("SELECT MAX(f.nroEntrega) FROM File f WHERE f.idAsociado = ?1")
    Integer getCurrentNroEntrega(UUID idAsociado);
}
