package tesis.backend.backend.evaluador.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tesis.backend.backend.evaluador.entity.Evaluador;

public interface EvaluadorRepository extends JpaRepository<Evaluador, UUID>{
    
}
