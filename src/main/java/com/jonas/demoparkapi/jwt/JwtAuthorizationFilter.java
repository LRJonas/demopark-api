package com.jonas.demoparkapi.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter { // classe que verifica se o usuário está autenticado

    @Autowired
    private JwtUserDetailsService detailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION); // pega o token do cabeçalho da requisição

        if (token == null || !token.startsWith(JwtUtils.JWT_BEARER)) { // verifica se o token é nulo ou não começa com o prefixo
            log.info("JWT Token está nulo, vazio ou não iniciado com 'Bearer '.");
            filterChain.doFilter(request, response); // se for nulo ou não começar com o prefixo, segue o fluxo normal
            return;
        }

        if (!JwtUtils.isValidToken(token)){ // verifica se o token é inválido
            log.warn("Token inválido");
            filterChain.doFilter(request, response); // se for inválido, segue o fluxo normal
            return;
        }

        String username = JwtUtils.getUsernameFromToken(token); // pega o nome de usuário do token

        toAuthentication(request, username);

        filterChain.doFilter(request, response); // se chegou até aqui, segue o fluxo normal
    }

    private void toAuthentication(HttpServletRequest request, String username) { // tendi foi nada

        UserDetails userDetails = detailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


    }
}
