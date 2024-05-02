package com.jonas.demoparkapi.web.controller;

import com.jonas.demoparkapi.entity.Vaga;
import com.jonas.demoparkapi.service.VagaService;
import com.jonas.demoparkapi.web.dto.VagaCreateDto;
import com.jonas.demoparkapi.web.dto.VagaRespondeDto;
import com.jonas.demoparkapi.web.dto.mapper.VagaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaRespondeDto> getByCodigo(@PathVariable String codigo){
        Vaga vaga = vagaService.buscaPorCodigo(codigo);

        return ResponseEntity.ok(VagaMapper.toDto(vaga));
    }
}
