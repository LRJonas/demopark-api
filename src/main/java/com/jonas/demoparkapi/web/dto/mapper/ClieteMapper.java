package com.jonas.demoparkapi.web.dto.mapper;
import com.jonas.demoparkapi.entity.Cliente;
import com.jonas.demoparkapi.web.dto.ClienteCreateDto;
import com.jonas.demoparkapi.web.dto.ClienteResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClieteMapper {

    public static Cliente toCliente(ClienteCreateDto dto){
       return new ModelMapper().map(dto, Cliente.class);
    }

    public static ClienteResponseDto toDto(Cliente cliente){
        return new ModelMapper().map(cliente, ClienteResponseDto.class);
    }
}
