package com.jonas.demoparkapi.config;

import com.jonas.demoparkapi.jwt.JwtAuthenticationEntryPoint;
import com.jonas.demoparkapi.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc // Anotação para habilitar o Spring MVC
@EnableMethodSecurity // Anotação para habilitar a segurança nos métodos
public class SpringSecurityConfig {

    private static final String[] DOCUMENTATION_OPENAPI = {
            "/docs/index.html",
            "/docs-park.html", "/docs-park/**",
            "/v3/api-docs/**",
            "/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**",
            "/**.html", "/webjars/**", "/configuration/**", "/swagger-resources/**"
    };


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception { // Método que retorna um SecurityFilterChain, que é uma cadeia de filtros de segurança

        return http
                .csrf(csrf -> csrf.disable()) // Desabilita o CSRF, estudar o que é CSRF
                .formLogin(formLogin -> formLogin.disable()) // Desabilita o formulário de login, pq o spring espera um formulário de login
                .httpBasic(httpBasic -> {})// Habilita o httpBasic
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "api/v1/usuarios").permitAll() // Permite o acesso ao endpoint de cadastro de usuários a qualquer um
                        .requestMatchers(HttpMethod.POST, "api/v1/auth").permitAll() // Permite o acesso ao endpoint de autenticação a qualquer um
                        .requestMatchers(DOCUMENTATION_OPENAPI).permitAll() // Permite o acesso a documentação a qualquer um
                        .anyRequest().authenticated() // Qualquer outra requisição precisa estar autenticada
                ).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Como é uma API REST, não é necessário manter o estado da sessão
                ).addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())) // Configura o tratamento de exceção de autenticação
                .build();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() { // Método que retorna um objeto de filtro de autorização JWT
        return new JwtAuthorizationFilter(); // Retorna um objeto de filtro de autorização JWT
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // Retorna um objeto de criptografia de senha
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception { // Método que retorna um objeto de autenticação
        return authenticationConfiguration.getAuthenticationManager(); // Retorna um objeto de autenticação
    }
}
