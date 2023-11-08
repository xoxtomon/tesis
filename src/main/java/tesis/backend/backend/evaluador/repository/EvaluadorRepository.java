package tesis.backend.backend.evaluador.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tesis.backend.backend.evaluador.entity.Evaluador;
import tesis.backend.backend.evaluador.entity.EvaluadorKey;

public interface EvaluadorRepository extends JpaRepository<Evaluador, UUID>{
    
}
