package com.jonas.demoparkapi.web.dto.mapper;

import com.jonas.demoparkapi.web.dto.PagebleDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE) //construtor privado para impedir instanciação
public class PagebleMapper {

    public static PagebleDto toDto(Page page){
        return new ModelMapper().map(page, PagebleDto.class);
    }
}
