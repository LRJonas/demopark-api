package com.jonas.demoparkapi.repository.projection;

public interface ClienteProjection { //projection é uma interface que define um contrato para a projeção de dados

    Long getId(); //método para pegar o id

    String getNome(); //método para pegar o nome

    String getCpf(); //método para pegar o cpf

}
