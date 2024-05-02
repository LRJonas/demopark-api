package com.jonas.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VagaCreateDto {

    @NotBlank
    @Size(min = 4, max = 4)
    private String codigo;

    @NotBlank
    @Pattern(regexp = "LIVRE|OCUPADA") // anotação para definir que o campo só aceita os valores LIVRE ou OCUPADA
    private String status;

}
