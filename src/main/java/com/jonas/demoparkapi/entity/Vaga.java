package com.jonas.demoparkapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "vagas")
public class Vaga implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING) // aqui ele pega o valor do Enum e salva como String no banco
    private StatusVaga status;

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

    public enum StatusVaga{
        LIVRE, OCUPADA
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vaga vaga)) return false;
        return Objects.equals(id, vaga.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
