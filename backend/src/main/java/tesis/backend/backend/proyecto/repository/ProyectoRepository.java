package tesis.backend.backend.proyecto.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tesis.backend.backend.proyecto.entity.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, UUID> {
    
}
