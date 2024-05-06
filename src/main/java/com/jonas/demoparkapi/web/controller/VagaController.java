package com.jonas.demoparkapi.web.controller;

import com.jonas.demoparkapi.entity.Vaga;
import com.jonas.demoparkapi.service.VagaService;
import com.jonas.demoparkapi.web.dto.VagaCreateDto;
import com.jonas.demoparkapi.web.dto.VagaRespondeDto;
import com.jonas.demoparkapi.web.dto.mapper.VagaMapper;
import com.jonas.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {

    private final VagaService vagaService;

    @Operation(
            summary = "Cria uma vaga",
            description = "Recurso para criar vagas",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Vaga criada com sucesso", headers = @Header(name = HttpHeaders.LOCATION, description = "URI para acessar a vaga criada")),
                    @ApiResponse(responseCode = "409", description = "Código já cadastrado", content = @Content(mediaType = "application/json;scharset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid VagaCreateDto dto){
        Vaga vaga = VagaMapper.toVaga(dto);
        vagaService.salvar(vaga);
        URI location = ServletUriComponentsBuilder //cria a URI para o recurso criado, está sendo passado pelo cabeçalho da resposta
                .fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(vaga.getCodigo())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Recupera uma vaga pelo código",
            description = "Recurso para localizar vagas pelo seu código",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vaga recuperada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VagaRespondeDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaRespondeDto> getByCodigo(@PathVariable String codigo){
        Vaga vaga = vagaService.buscaPorCodigo(codigo);

        return ResponseEntity.ok(VagaMapper.toDto(vaga));
    }
}
