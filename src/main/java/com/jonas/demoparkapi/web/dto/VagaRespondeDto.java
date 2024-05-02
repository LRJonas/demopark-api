package com.jonas.demoparkapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VagaRespondeDto {

    private Long id;
    private String codigo;
    private String status;

}
