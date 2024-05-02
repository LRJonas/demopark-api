package com.jonas.demoparkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class UsuarioLoginDto {
    @NotBlank(message = "Nome de usuário é obrigatório")
    @Email(regexp = ".+@.+\\..+", message = "Email inválido")
    private String username;
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;
}
