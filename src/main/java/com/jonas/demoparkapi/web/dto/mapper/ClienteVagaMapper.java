package com.jonas.demoparkapi.web.dto.mapper;

import com.jonas.demoparkapi.entity.ClienteVaga;
import com.jonas.demoparkapi.web.dto.EstacionamentoCreateDto;
import com.jonas.demoparkapi.web.dto.EstacionamentoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

    public static ClienteVaga toClienteVaga(EstacionamentoCreateDto dto){
        return new ModelMapper().map(dto, ClienteVaga.class);
    }

    public static EstacionamentoResponseDto toDto(ClienteVaga clienteVaga){
        return new ModelMapper().map(clienteVaga, EstacionamentoResponseDto.class);
    }
}
