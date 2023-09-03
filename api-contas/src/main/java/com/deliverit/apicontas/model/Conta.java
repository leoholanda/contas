package com.deliverit.apicontas.model;

import com.deliverit.apicontas.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @Column(name = "valor_original")
    private BigDecimal valorOriginal;

    @Column(name = "valor_corrigido")
    private BigDecimal valorCorrigido;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataPagamento;

    @Column(name = "quantidade_dias_atrasado")
    private Long diasAtrasado;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDENTE;

    @NotNull
    private Boolean ativo = true;

}
