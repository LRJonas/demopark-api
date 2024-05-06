package com.jonas.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder //Builder é um padrão de projeto de software criacional que permite a construção de objetos complexos passo a passo.
public class EstacionamentoCreateDto {

    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "placa deve conter XXX-0000") //[A-Z]{3} delimita que haverá 3 letras de A a Z [0-9]{4} delimita que haverá 4 números de 0 a 9
    private String placa;

    @NotBlank
    private String marca;
    @NotBlank
    private String modelo;
    @NotBlank
    private String cor;
    @NotBlank
    @Size(min = 11, max = 11)
    @CPF
    private String clienteCpf;

}
