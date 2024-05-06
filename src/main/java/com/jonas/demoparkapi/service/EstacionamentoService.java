package com.jonas.demoparkapi.service;

import com.jonas.demoparkapi.entity.Cliente;
import com.jonas.demoparkapi.entity.ClienteVaga;
import com.jonas.demoparkapi.entity.Vaga;
import com.jonas.demoparkapi.util.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final VagaService vagaService;
    private final ClienteService clienteService;

    @Transactional
    public ClienteVaga checkIn(ClienteVaga clienteVaga){

        Cliente cliente = clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf()); // busca o cliente pelo cpf
        clienteVaga.setCliente(cliente); // seta o cliente no clienteVaga

        Vaga vaga = vagaService.buscaPorVagaLivre(); // busca a primeira vaga livre
        vaga.setStatus(Vaga.StatusVaga.OCUPADA); // seta a vaga como ocupada

        clienteVaga.setVaga(vaga); // seta a vaga no clienteVaga

        clienteVaga.setDataEntrada(LocalDateTime.now());// seta a data de entrada

        System.out.println("DATA ------>>>>> " + clienteVaga.getDataEntrada());

        clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

        return clienteVagaService.salvar(clienteVaga); // salva o clienteVaga
    }

    @Transactional
    public ClienteVaga checkOut(String recibo) {

        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo); // busca o clienteVaga pelo recibo

        LocalDateTime dataSaida = LocalDateTime.now(); // seta a data de saída

        BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVaga.getDataEntrada(), dataSaida);// calcula o valor

        clienteVaga.setValor(valor); // seta o valor no clienteVaga

        long totalDeVezes = clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf()); // pega o total de vezes que o cliente estacionou

        BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor, totalDeVezes); // calcula o desconto
        clienteVaga.setDesconto(desconto); // seta o desconto no clienteVaga

        clienteVaga.setDataSaida(dataSaida); // seta a data de saída

        clienteVaga.getVaga().setStatus(Vaga.StatusVaga.LIVRE); // seta a vaga como livre

        return clienteVagaService.salvar(clienteVaga); // salva o clienteVaga
    }
}
