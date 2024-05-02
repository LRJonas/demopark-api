package com.jonas.demoparkapi.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UsuarioResponseDto {

    private Long id;
    private String username;
    private String role;
}
