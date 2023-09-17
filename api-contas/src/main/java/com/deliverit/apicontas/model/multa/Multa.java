package com.deliverit.apicontas.model.multa;

import com.deliverit.apicontas.model.Conta;

import java.math.BigDecimal;

public interface Multa {

    Conta calcularMulta(Conta conta, Long quantidadeDiasEmAtraso);
}
