package com.jonas.demoparkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
public class UsuarioCreateDto {
    //NÃO PERMITE QUE O CAMPO EMAIL SEJA NULO OU VAZIO
    @NotBlank(message = "Nome de usuário é obrigatório") // validação de campo obrigatório, o message é a mensagem que será exibida caso o campo esteja vazio
    @Email(regexp = ".+@.+\\..+", message = "Email inválido") // validação de email, o message é a mensagem que será exibida caso o email seja inválido
    private String username;

    @NotBlank(message = "Senha é obrigatória") // validação de campo obrigatório, o message é a mensagem que será exibida caso o campo esteja vazio
    @Size(min = 6, max = 6, message = "A senha deve ter no mínimo 6 caracteres") // validação de tamanho mínimo, o message é a mensagem que será exibida caso a senha tenha menos de 6 caracteres
    private String password;
}
