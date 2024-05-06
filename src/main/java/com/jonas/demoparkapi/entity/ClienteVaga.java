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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cliente_tem_vagas")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClienteVaga {  // classe para representar a relação entre cliente e vaga

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_recibo", nullable = false, length = 15)
    private String recibo;

    @Column(name = "placa", nullable = false, length = 8)
    private String placa;

    @Column(name = "marca", nullable = false, length = 20)
    private String marca;

    @Column(name = "modelo", nullable = false, length = 20)
    private String modelo;

    @Column(name = "cor", nullable = false, length = 20)
    private String cor;

    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;

    @Column(name = "data_saida")
    private LocalDateTime dataSaida;

    @Column(name = "valor", columnDefinition = "DECIMAL(7,2)") // definição de coluna com precisão de 7 digitos e 2 casas decimais
    private BigDecimal valor;

    @Column(name = "desconto", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal desconto;

    @ManyToOne //anotação para definir que a relação é de muitos para um
    @JoinColumn(name = "id_cliente", nullable = false) //anotação para definir a chave estrangeira
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_vaga", nullable = false)
    private Vaga vaga;

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
        if (!(o instanceof ClienteVaga that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
