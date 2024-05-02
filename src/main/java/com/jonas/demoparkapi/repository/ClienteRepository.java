package com.jonas.demoparkapi.repository;

import com.jonas.demoparkapi.entity.Cliente;
import com.jonas.demoparkapi.repository.projection.ClienteProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c")
    Page<ClienteProjection> findAllPageble (Pageable pageable);

    Cliente findByUsuarioId(Long id);
}
