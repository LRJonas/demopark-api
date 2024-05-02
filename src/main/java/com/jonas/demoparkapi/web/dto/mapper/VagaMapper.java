package com.jonas.demoparkapi.web.dto.mapper;

import com.jonas.demoparkapi.entity.Vaga;
import com.jonas.demoparkapi.web.dto.VagaCreateDto;
import com.jonas.demoparkapi.web.dto.VagaRespondeDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDto dto){

        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaRespondeDto toDto(Vaga vaga){
        return new ModelMapper().map(vaga, VagaRespondeDto.class);
    }
}
