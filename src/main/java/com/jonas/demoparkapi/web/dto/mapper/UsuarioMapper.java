package com.jonas.demoparkapi.web.dto.mapper;

import com.jonas.demoparkapi.entity.Usuario;
import com.jonas.demoparkapi.web.dto.UsuarioCreateDto;
import com.jonas.demoparkapi.web.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto createDto){
        return new ModelMapper().map(createDto, Usuario.class);
    }

    public static UsuarioResponseDto toUsuarioResponseDto(Usuario usuario){
        String role = usuario.getRole().name().substring("ROLE_".length()); // aqui está pegando somente o valor do enum após o "ROLE_"
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() { // essa função é colocar o valor do enum tratado no DTO
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper modelMapper = new ModelMapper(); // aqui está criando um ModelMapper
        modelMapper.addMappings(props); // aqui está adicionando o mapeamento

        return modelMapper.map(usuario, UsuarioResponseDto.class); // aqui está fazendo o mapeamento do usuário para o DTO
    }

    public  static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios){
        return usuarios.stream().map(user -> toUsuarioResponseDto(user)).collect(Collectors.toList());
    }
}
