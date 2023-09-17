package com.deliverit.apicontas.model.multa;

import com.deliverit.apicontas.model.Conta;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CorrecaoPorAtraso {

    public Conta calcular(Multa multa, Conta conta, Long quantidadeDiasEmAtraso) {
        return multa.calcularMulta(conta, quantidadeDiasEmAtraso);
    }
}
