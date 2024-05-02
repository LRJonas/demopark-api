package com.jonas.demoparkapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.cache.interceptor.SimpleKeyGenerator.generateKey;

@Slf4j
public class JwtUtils {

    public static final String JWT_BEARER = "Bearer "; // Prefixo do token JWT
    public static final String JWT_AUTHORIZATION = "Authorization"; // Nome do cabeçalho de autorização
    public static final String SECRET_KEY = "12345678901234567890123456789012"; // Chave secreta para criptografia do token, PRECISA SER 32 CARACTERES, dps ela é alterada
    public static final long EXPIRE_DAYS = 0; // Tempo de expiração do token, em dias
    public static final long EXPIRE_HOURS = 0; // Tempo de expiração do token, em horas
    public static final long EXPIRE_MINUTES = 30; // Tempo de expiração do token, em minutos

    public JwtUtils() {
    }

    private static Key generateKey() { // Método que retorna uma chave de segurança
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); // Retorna uma chave de segurança
    }

    private static Date toExpireDate (Date start){
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(); // Converte a data para um objeto LocalDateTime
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES); // Adiciona o tempo de expiração ao objeto LocalDateTime
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant()); // Converte o objeto LocalDateTime para um objeto Date
    }

    public static JwtToken createToken(String username, String role){ // Método que cria um token JWT
        Date issuedAt = new Date(); // Data de emissão do token
        Date limit = toExpireDate(issuedAt); // Data de expiração do token
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT") // Define o tipo do token
                .setSubject(username) // Define o assunto do token
                .setIssuedAt(issuedAt) // Define a data de emissão do token
                .setExpiration(limit) // Define a data de expiração do token
                .signWith(generateKey(), SignatureAlgorithm.HS256) // Define a chave de segurança do token
                .claim("role", role) // Adiciona um campo a mais no token
                .compact(); // Compacta o token

        return new JwtToken(token); // Retorna o token
    }

    private static Claims getClaimsFromToken(String token){ // Método que retorna o conteúdo do token
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(refactorToken(token))
                    .getBody();
        } catch (JwtException e){
            log.error(String.format("Token inválido: %s", e.getMessage()));
        }
        return null;
    }

    public static String getUsernameFromToken(String token){ // Método que retorna o nome de usuário do token

        return getClaimsFromToken(token).getSubject(); // Retorna o nome de usuário do token

    }

    public static boolean isValidToken (String token){ // Método que verifica se o token é válido
        try{
            Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(refactorToken(token));

            return true;

        } catch (JwtException e){
            log.error(String.format("Token inválido: %s", e.getMessage()));
        }
        return false;
    }

    private static String refactorToken(String token){ // Método que retira o prefixo do token
        if(token.contains(JWT_BEARER)){
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }



}
