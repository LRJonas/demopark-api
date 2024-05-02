package com.jonas.demoparkapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing // Anotação para habilitar a auditoria JPA
@Configuration                                                          // Se fosse pra auditar pelo ID do usuário, seria necessário implementar a interface AuditorAware<Long> e retornar o ID do usuário logado
public class SpringJpaAuditingConfig implements AuditorAware<String> { // O valor genérico é String pq o tipo do campo que armazena o nome do usuário é String
    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Pega o usuário autenticado

        if(authentication != null && authentication.isAuthenticated()) // Se o usuário estiver autenticado
            return Optional.of(authentication.getName()); // Retorna o nome do usuário autenticado

        return null;
    }
}
