package com.jonas.demoparkapi;

import com.jonas.demoparkapi.web.dto.UsuarioCreateDto;
import com.jonas.demoparkapi.web.dto.UsuarioResponseDto;
import com.jonas.demoparkapi.web.dto.UsuarioSenhaDto;
import com.jonas.demoparkapi.web.exception.ErrorMessage;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //anotação para indicar que é um teste de integração
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) //anotação para executar um script sql antes do teste
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) //anotação para executar um script sql depois do teste
public class UsuarioIT {

    @Autowired
    WebTestClient webTestClient; //injeção do webTestClient

    @Test //anotação para indicar que é um teste
    public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201(){ // precisa ser void e não receber parâmetros

        UsuarioResponseDto responseBody = webTestClient //cria um usuário com o webTestClient
                .post()                 //método post
                .uri("/api/v1/usuarios") //uri do recurso
                .contentType(MediaType.APPLICATION_JSON) //tipo de conteúdo
                .bodyValue(new UsuarioCreateDto("criar_usuario@gmail.com", "123456")) // corpo da requisição
                .exchange()           //executa a requisição
                .expectStatus().isCreated() //espera que o status seja 201
                .expectBody(UsuarioResponseDto.class) //espera que o corpo da resposta seja um usuário
                .returnResult()     //retorna o resultado
                .getResponseBody(); //pega o corpo da resposta

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull(); //verifica se o id é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("criar_usuario@gmail.com").isNotNull(); //verifica se o username é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE").isNotNull(); //verifica se o username é diferente de nulo
    }

    @Test //anotação para indicar que é um teste
    public void createUsuario_ComUsernameEPasswordInvalidos_RetornarErrosMessageStatus422(){ // precisa ser void e não receber parâmetros

        ErrorMessage responseBody = webTestClient //cria um usuário com o webTestClient
                .post()                 //método post
                .uri("/api/v1/usuarios") //uri do recurso
                .contentType(MediaType.APPLICATION_JSON) //tipo de conteúdo
                .bodyValue(new UsuarioCreateDto("", "123456")) // corpo da requisição
                .exchange()           //executa a requisição
                .expectStatus().isEqualTo(422) //espera que o status seja 201
                .expectBody(ErrorMessage.class) //espera que o corpo da resposta seja um usuário
                .returnResult()
                .getResponseBody();     //retorna o resultado

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422); //verifica se o status do erro é 422

        responseBody = webTestClient //cria um usuário com o webTestClient
                .post()                 //método post
                .uri("/api/v1/usuarios") //uri do recurso
                .contentType(MediaType.APPLICATION_JSON) //tipo de conteúdo
                .bodyValue(new UsuarioCreateDto("kiko", "123456")) // corpo da requisição
                .exchange()           //executa a requisição
                .expectStatus().isEqualTo(422) //espera que o status seja 201
                .expectBody(ErrorMessage.class) //espera que o corpo da resposta seja um usuário
                .returnResult()
                .getResponseBody();     //retorna o resultado

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422); //verifica se o status do erro é 422

        responseBody = webTestClient //cria um usuário com o webTestClient
                .post()                 //método post
                .uri("/api/v1/usuarios") //uri do recurso
                .contentType(MediaType.APPLICATION_JSON) //tipo de conteúdo
                .bodyValue(new UsuarioCreateDto("kiko@zinho.", "123456")) // corpo da requisição
                .exchange()           //executa a requisição
                .expectStatus().isEqualTo(422) //espera que o status seja 201
                .expectBody(ErrorMessage.class) //espera que o corpo da resposta seja um usuário
                .returnResult()
                .getResponseBody();     //retorna o resultado

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422); //verifica se o status do erro é 422
    }
    @Test
    public void createUsuario_ComPasswordInvalidos_RetornarErrosMessageStatus422(){ // precisa ser void e não receber parâmetros

        ErrorMessage responseBody = webTestClient //cria um usuário com o webTestClient
                .post()                 //método post
                .uri("/api/v1/usuarios") //uri do recurso
                .contentType(MediaType.APPLICATION_JSON) //tipo de conteúdo
                .bodyValue(new UsuarioCreateDto("kiko@zinho.com", "")) // corpo da requisição
                .exchange()           //executa a requisição
                .expectStatus().isEqualTo(422) //espera que o status seja 201
                .expectBody(ErrorMessage.class) //espera que o corpo da resposta seja um usuário
                .returnResult()
                .getResponseBody();     //retorna o resultado

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422); //verifica se o status do erro é 422

        responseBody = webTestClient //cria um usuário com o webTestClient
                .post()                 //método post
                .uri("/api/v1/usuarios") //uri do recurso
                .contentType(MediaType.APPLICATION_JSON) //tipo de conteúdo
                .bodyValue(new UsuarioCreateDto("kiko@zinho.com", "12345600000")) // corpo da requisição
                .exchange()           //executa a requisição
                .expectStatus().isEqualTo(422) //espera que o status seja 201
                .expectBody(ErrorMessage.class) //espera que o corpo da resposta seja um usuário
                .returnResult()
                .getResponseBody();     //retorna o resultado

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422); //verifica se o status do erro é 422

        responseBody = webTestClient //cria um usuário com o webTestClient
                .post()                 //método post
                .uri("/api/v1/usuarios") //uri do recurso
                .contentType(MediaType.APPLICATION_JSON) //tipo de conteúdo
                .bodyValue(new UsuarioCreateDto("kiko@zinho.", "123")) // corpo da requisição
                .exchange()           //executa a requisição
                .expectStatus().isEqualTo(422) //espera que o status seja 201
                .expectBody(ErrorMessage.class) //espera que o corpo da resposta seja um usuário
                .returnResult()
                .getResponseBody();     //retorna o resultado

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422); //verifica se o status do erro é 422
    }

    @Test //anotação para indicar que é um teste
    public void createUsuario_ComUsernameRepetido_RetornarUsuarioCriadoComStatus409(){ // precisa ser void e não receber parâmetros

        ErrorMessage responseBody = webTestClient //cria um usuário com o webTestClient
                .post()                 //método post
                .uri("/api/v1/usuarios") //uri do recurso
                .contentType(MediaType.APPLICATION_JSON) //tipo de conteúdo
                .bodyValue(new UsuarioCreateDto("kiko@gmail.com", "123456")) // corpo da requisição
                .exchange()           //executa a requisição
                .expectStatus().isEqualTo(409) //espera que o status seja 409
                .expectBody(ErrorMessage.class) //espera que o corpo da resposta seja um usuário
                .returnResult()     //retorna o resultado
                .getResponseBody(); //pega o corpo da resposta

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409); //verifica se o id é diferente de nulo

    }

    @Test //anotação para indicar que é um teste
    public void buscarUsuario_ComIdExistente_RetornarUsuarioCriadoComStatus200(){ // precisa ser void e não receber parâmetros

        UsuarioResponseDto responseBody = webTestClient //cria um usuário com o webTestClient
                .get()                 //método post
                .uri("/api/v1/usuarios/501") //uri do recurso
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient,"admin@gmail.com", "123456" ))
                .exchange()           //executa a requisição
                .expectStatus().isOk() //espera que o status seja 200
                .expectBody(UsuarioResponseDto.class) //espera que o corpo da resposta seja um usuário
                .returnResult()     //retorna o resultado
                .getResponseBody(); //pega o corpo da resposta

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(501); //verifica se o id é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("admin@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

        responseBody = webTestClient //cria um usuário com o webTestClient
                .get()                 //método post
                .uri("/api/v1/usuarios/500") //uri do recurso
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient,"admin@gmail.com", "123456" ))
                .exchange()           //executa a requisição
                .expectStatus().isOk() //espera que o status seja 200
                .expectBody(UsuarioResponseDto.class) //espera que o corpo da resposta seja um usuário
                .returnResult()     //retorna o resultado
                .getResponseBody(); //pega o corpo da resposta

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(500); //verifica se o id é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("kiko@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

        responseBody = webTestClient //cria um usuário com o webTestClient
                .get()                 //método post
                .uri("/api/v1/usuarios/500") //uri do recurso
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient,"kiko@gmail.com", "123456" ))
                .exchange()           //executa a requisição
                .expectStatus().isOk() //espera que o status seja 200
                .expectBody(UsuarioResponseDto.class) //espera que o corpo da resposta seja um usuário
                .returnResult()     //retorna o resultado
                .getResponseBody(); //pega o corpo da resposta

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(500); //verifica se o id é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("kiko@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

    }

    @Test //anotação para indicar que é um teste
    public void buscarUsuario_ComIdInexistente_RetornarUsuarioCriadoComStatus404(){ // precisa ser void e não receber parâmetros

        ErrorMessage responseBody = webTestClient //cria um usuário com o webTestClient
                .get()                 //método post
                .uri("/api/v1/usuarios/0") //uri do recurso
                .headers(JwtAuthentication.getHeaderAuthorization(webTestClient,"admin@gmail.com", "123456" ))
                .exchange()           //executa a requisição
                .expectStatus().isNotFound() //espera que o status seja 200
                .expectBody(ErrorMessage.class) //espera que o corpo da resposta seja um usuário
                .returnResult()     //retorna o resultado
                .getResponseBody(); //pega o corpo da resposta

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //verifica se o responseBody é diferente de nulo
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404); //verifica se o id é diferente de nulo

    }

    @Test //anotação para indicar que é um teste
    public void editarSenha_ComDadosValidos_RetornarStatus204(){ // precisa ser void e não receber parâmetros

        webTestClient //cria um usuário com o webTestClient
                .patch()                 //método post
                .uri("/api/v1/usuarios/100") //uri do recurso
                .contentType(MediaType.APPLICATION_JSON) //tipo de conteúdo
                .bodyValue(new UsuarioSenhaDto("123456", "654321", "654321")) // corpo da requisição
                .exchange()           //executa a requisição
                .expectStatus().isNoContent(); //espera que o status seja 204;


    }


}
