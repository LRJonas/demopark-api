package com.jonas.demoparkapi.web.controller;

import com.jonas.demoparkapi.entity.Usuario;
import com.jonas.demoparkapi.exception.UsernameUniqueViolationException;
import com.jonas.demoparkapi.service.UsuarioService;
import com.jonas.demoparkapi.web.dto.UsuarioCreateDto;
import com.jonas.demoparkapi.web.dto.UsuarioResponseDto;
import com.jonas.demoparkapi.web.dto.UsuarioSenhaDto;
import com.jonas.demoparkapi.web.dto.mapper.UsuarioMapper;
import com.jonas.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Usuários", description = "CRUD de usuários") //anotação para documentação do swagger
@RequiredArgsConstructor //anotação para gerar um construtor com as dependências
@RestController //anotação para indicar que é um controller
@RequestMapping("/api/v1/usuarios") //anotação para indicar o caminho do recurso
public class UsuarioController {

    private final UsuarioService usuarioService; //injeção de dependência do serviço, precisa ser final para ser injetado

    // anotação para especificar a operação, com descrição e respostas possíveis no docs-park.html
    @Operation(summary = "Cria um usuário", description = "Recurso para criar usuários",
               responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                       @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
               })
    @PostMapping //anotação para indicar que é um método POST e o @Valid para validar o objeto com as anotações do DTO
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto) { //Request body indica que o usuário será passado no corpo da requisição

        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto)); //chamada do método create do serviço
                                                                                // e passando o usuário convertido do dto

        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toUsuarioResponseDto(user)); //retorna o usuário salvo
    }

    @Operation(summary = "Recupera um usuário pelo id", description = "Recurso para localizar usuários pelo seu id",
            security = @SecurityRequirement(name = "security"), //anotação para indicar que é necessário autenticação e foi configurada no SpringSecurityConfig
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário recuperado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENTE') AND #id == authentication.principal.id)") //anotação para indicar que apenas usuários com a role ADMIN podem acessar tudo e usuários com a role CLIENTE só podem acessar o seu próprio recurso
    @GetMapping("/{id}") //anotação para indicar que é um método GET e que o id será passado como parâmetro
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){ //Path variable pega o id passado na url

        Usuario user = usuarioService.buscarPorId(id); //chamada do método create do serviço

        return ResponseEntity.ok(UsuarioMapper.toUsuarioResponseDto(user)); //retorna o usuário encontrado
    }

    @Operation(summary = "Atualizar senha", description = "Recurso para atualizar senha",
            security = @SecurityRequirement(name = "security"), //anotação para indicar que é necessário autenticação e foi configurada no SpringSecurityConfig
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            })
    @PatchMapping("/{id}") //patch por ser uma atualização parcial, se fosse total era put
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE') AND (#id == authentication.principal.id)") //anotação para indicar que apenas usuários com a role ADMIN podem acessar tudo e usuários com a role CLIENTE só podem acessar o seu próprio recurso
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto usuarioSenhaDto){ //Request body indica que a senha será passado no corpo da requisição através de uma DTO

        Usuario user = usuarioService.editarSenha(id, usuarioSenhaDto.getSenhaAtual(), usuarioSenhaDto.getNovaSenha(), usuarioSenhaDto.getConfirmaSenha()); //aqui está chamando o método de editar passando o id e os atributos da DTO

        return ResponseEntity.noContent().build(); //retorna apenas o status 204
    }

    @Operation(summary = "Listar todos os usuários", description = "Recurso para listar os usuários",
            security = @SecurityRequirement(name = "security"), //anotação para indicar que é necessário autenticação e foi configurada no SpringSecurityConfig
            responses = {
                    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "200", description = "Recurso feito com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") //anotação para indicar que apenas usuários com a role ADMIN podem acessar
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){

            List<Usuario> users = usuarioService.buscarTodos(); //chamada do método de buscar todos do serviço

            return ResponseEntity.ok(UsuarioMapper.toListDto(users)); //retorna a lista de usuários
    }
}
