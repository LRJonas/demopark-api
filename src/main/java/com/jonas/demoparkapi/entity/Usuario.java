package com.jonas.demoparkapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor //anotações do lombok para gerar os métodos getters, setters e construtor sem argumentos
@Entity
@EntityListeners(AuditingEntityListener.class) //anotação para habilitar a auditoria JPA
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Aqui está definindo que o campo username é obrigatório, único e tem no máximo 100 caracteres
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    // Aqui está definindo que o campo password é obrigatório e tem no máximo 200 caracteres por causa da criptografia
    @Column(name= "password", nullable = false, length = 200)
    private String password;

    @Enumerated(EnumType.STRING) // Aqui está definindo que o campo role é uma enumeração e será salvo como string e não como número (0 ou 1)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENTE;

    @CreatedDate //anotação para definir que o campo é de data de criação
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @LastModifiedDate // anotação para definir que o campo é de data de modificação
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;

    @CreatedBy //anotação para definir que o campo é de quem criou
    @Column(name = "criado_por")
    private String criadoPor;

    @LastModifiedBy //anotação para definir que o campo é de quem modificou
    @Column(name = "modificado_por")
    private String modificadoPor;

    public enum Role {
        ROLE_ADMIN, ROLE_CLIENTE // precisa ser ROLE_ADMIN e ROLE_CLIENTE para funcionar com o Spring Security
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                '}';
    }
}
