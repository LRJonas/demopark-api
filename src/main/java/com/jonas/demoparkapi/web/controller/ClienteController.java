package com.jonas.demoparkapi.web.controller;

import com.jonas.demoparkapi.entity.Cliente;
import com.jonas.demoparkapi.jwt.JwtUserDetails;
import com.jonas.demoparkapi.repository.projection.ClienteProjection;
import com.jonas.demoparkapi.service.ClienteService;
import com.jonas.demoparkapi.service.UsuarioService;
import com.jonas.demoparkapi.web.dto.ClienteCreateDto;
import com.jonas.demoparkapi.web.dto.ClienteResponseDto;
import com.jonas.demoparkapi.web.dto.PagebleDto;
import com.jonas.demoparkapi.web.dto.UsuarioResponseDto;
import com.jonas.demoparkapi.web.dto.mapper.ClieteMapper;
import com.jonas.demoparkapi.web.dto.mapper.PagebleMapper;
import com.jonas.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Clientes", description = "CRUD de clientes")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @Operation(summary = "Cria um cliente", description = "Recurso para criar cliente vinculado a um usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Nome já cadastrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create (@RequestBody @Valid ClienteCreateDto dto,
                                                      @AuthenticationPrincipal JwtUserDetails userDetails) { // anotação para injetar o usuário autenticado
        Cliente cliente = ClieteMapper.toCliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.salvar(cliente);
        return ResponseEntity.status(201).body(ClieteMapper.toDto(cliente));
    }

    @Operation(summary = "busca um cliente pelo id", description = "Recurso para buscar cliente vinculado a um usuário",
            responses = {
                    @ApiResponse(responseCode = "200,", description = "Recurso buscado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ClieteMapper.toDto(cliente));
    }


    @Operation(summary = "Recuperar lista de clientes",
            description = "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN' ",
            security = @SecurityRequirement(name = "security"),
            parameters = {                                          //anotação para especificar os parâmetros da operação
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Representa a página retornada"
                    ),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5")),
                            description = "Representa o total de elementos por página"
                    ),
                    @Parameter(in = QUERY, name = "sort", hidden = true, //o hidden = true faz com que o parâmetro não seja exibido na documentação
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "nome,asc")),
                            description = "Representa a ordenação dos resultados. Aceita multiplos critérios de ordenação são suportados.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClienteResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    //localhost:8080/api/v1/clientes?size=1&page=1 size é a quantidade de elementos por página e page é a página que deseja acessar começando por 0
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagebleDto> getAll(@Parameter(hidden = true)
                                                 @PageableDefault(size=5, sort={"nome"}) Pageable pageable){ //Pageable é uma interface do Spring que permite a paginação
        Page<ClienteProjection> clientes = clienteService.buscarTodos(pageable);
        return ResponseEntity.ok(PagebleMapper.toDto(clientes));
    }

    @Operation(summary = "Recuperar dados do cliente autenticado",
            description = "Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENTE'",
            security = @SecurityRequirement(name = "security"), // anotação para indicar que é necessário autenticação e foi configurada no SpringSecurityConfig
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClienteResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de ADMIN",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping("/detalhes")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> getDetalhes(@AuthenticationPrincipal JwtUserDetails userDetails){ //Pageable é uma interface do Spring que permite a paginação

        Cliente cliente = clienteService.buscarPorUsuario(userDetails.getId());
        return ResponseEntity.ok(ClieteMapper.toDto(cliente));
    }
}
