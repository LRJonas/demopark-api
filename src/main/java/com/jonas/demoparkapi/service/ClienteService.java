package com.jonas.demoparkapi.service;

import com.jonas.demoparkapi.entity.Cliente;
import com.jonas.demoparkapi.repository.ClienteRepository;
import com.jonas.demoparkapi.repository.projection.ClienteProjection;
import com.jonas.demoparkapi.web.exception.CpfUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
     public Cliente salvar(Cliente cliente){
        try{
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e){
            throw new CpfUniqueViolationException(String.format("CPF %s já cadastrado", cliente.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente com id %d não encontrado", id))
        );
    }
    @Transactional(readOnly = true)
    public Page<ClienteProjection> buscarTodos(Pageable pageable) { //Pageable é uma interface do Spring que permite a paginação
        return clienteRepository.findAllPageble(pageable);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorUsuario(Long id) { // método para buscar cliente por id do usuário, somente o usuário autenticado pode acessar os dados do cliente
        return clienteRepository.findByUsuarioId(id);
    }
}
