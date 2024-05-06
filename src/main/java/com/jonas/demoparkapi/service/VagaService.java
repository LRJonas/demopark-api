package com.jonas.demoparkapi.service;

import com.jonas.demoparkapi.entity.Vaga;
import com.jonas.demoparkapi.repository.VagaRepository;
import com.jonas.demoparkapi.web.exception.CodigoUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.jonas.demoparkapi.entity.Vaga.StatusVaga.LIVRE;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga salvar (Vaga vaga){
        try{
            return vagaRepository.save(vaga);
        } catch (DataIntegrityViolationException e){
            throw new CodigoUniqueViolationException(String.format("Código %s já cadastrado", vaga.getCodigo()));
        }
    }

    @Transactional(readOnly = true)
    public Vaga buscaPorCodigo(String codigo){
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException(String.format("Vaga com código %s não encontrada", codigo))
        );
    }

    @Transactional(readOnly = true)
    public Vaga buscaPorVagaLivre() {
        return vagaRepository.findFirstByStatus(LIVRE).orElseThrow(
                () -> new EntityNotFoundException("Nenhuma vaga livre encontrada")
        );
    }
}
