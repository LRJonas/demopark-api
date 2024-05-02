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

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "clientes")
@EntityListeners(AuditingEntityListener.class)
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @OneToOne //anotação para definir que a relação é de um para um
    @JoinColumn(name = "usuario_id", nullable = false) //anotação para definir a chave estrangeira
    private Usuario usuario;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente cliente)) return false;
        return Objects.equals(getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
