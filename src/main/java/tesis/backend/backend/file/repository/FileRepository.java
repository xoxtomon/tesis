package tesis.backend.backend.file.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tesis.backend.backend.file.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
    
}
