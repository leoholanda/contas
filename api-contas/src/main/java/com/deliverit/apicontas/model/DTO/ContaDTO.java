package com.deliverit.apicontas.model.DTO;

import com.deliverit.apicontas.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaDTO {

    private String nome;
    private BigDecimal valorOriginal;
    private BigDecimal valorCorrigido;
    private int diasDeAtraso;
    private LocalDate dataVencimento;
    private LocalDateTime dataPagamento;
    private Status status;
    private Boolean ativo;
}
