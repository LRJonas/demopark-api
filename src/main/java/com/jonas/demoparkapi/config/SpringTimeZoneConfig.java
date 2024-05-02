package com.jonas.demoparkapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration //anotação para indicar que é uma classe de configuração
public class SpringTimeZoneConfig {

    @PostConstruct //anotação para indicar que o método deve ser executado após a inicialização do construtor
    public void setTimeZone(){

        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}
