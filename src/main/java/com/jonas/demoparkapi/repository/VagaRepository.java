package com.jonas.demoparkapi.repository;

import com.jonas.demoparkapi.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VagaRepository extends JpaRepository<Vaga, Long>{
    Optional<Vaga> findByCodigo(String codigo);

    Optional<Vaga> findFirstByStatus(Vaga.StatusVaga status);
}
