package com.jonas.demoparkapi.jwt;

import com.jonas.demoparkapi.entity.Usuario;
import com.jonas.demoparkapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService { // Classe que é usada para localizar o usuário no banco de dados

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorUsername(username); // Busca o usuário no banco de dados
        return new JwtUserDetails(usuario); // Retorna um objeto do tipo UserDetails
    }

    public JwtToken getTokenAuthenticated(String username){
        Usuario.Role role = usuarioService.buscarRolePorUsername(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length())); // Retorna um token JWT
    }
}
