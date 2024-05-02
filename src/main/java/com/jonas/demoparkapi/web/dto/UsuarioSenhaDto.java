package com.jonas.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class UsuarioSenhaDto {

    @NotBlank(message = "Senha é obrigatória") // validação de campo obrigatório, o message é a mensagem que será exibida caso o campo esteja vazio
    @Size(min = 6, max = 6, message = "A senha deve ter no mínimo 6 caracteres e no máximo 6") // validação de tamanho mínimo, o message é a mensagem que será exibida caso a senha tenha menos de 6 caracteres
    private String senhaAtual;
    @NotBlank(message = "Senha é obrigatória") // validação de campo obrigatório, o message é a mensagem que será exibida caso o campo esteja vazio
    @Size(min = 6, max = 6, message = "A senha deve ter no mínimo 6 caracteres e no máximo 6") // validação de tamanho mínimo, o message é a mensagem que será exibida caso a senha tenha menos de 6 caracteres
    private String novaSenha;
    @NotBlank(message = "Senha é obrigatória") // validação de campo obrigatório, o message é a mensagem que será exibida caso o campo esteja vazio
    @Size(min = 6, max = 6, message = "A senha deve ter no mínimo 6 caracteres e no máximo 6") // validação de tamanho mínimo, o message é a mensagem que será exibida caso a senha tenha menos de 6 caracteres
    private String confirmaSenha;
}
