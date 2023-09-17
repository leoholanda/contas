package com.deliverit.apicontas.model.multa;

import com.deliverit.apicontas.model.Conta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MultaPorAtrasoSuperiorTresDias implements Multa {

    @Override
    public Conta calcularMulta(Conta conta, Long quantidadeDiasEmAtraso) {
        if (quantidadeDiasEmAtraso > 3 && quantidadeDiasEmAtraso <= 5) {
            BigDecimal multa = conta.getValorOriginal().multiply(BigDecimal.valueOf(0.03));
            BigDecimal juros = BigDecimal.valueOf(0.002)
                    .multiply(conta.getValorOriginal())
                    .multiply(BigDecimal.valueOf(quantidadeDiasEmAtraso));

            conta.setDiasAtrasado(quantidadeDiasEmAtraso < 0 ? 0 : quantidadeDiasEmAtraso);
            conta.setValorCorrigido(conta.getValorOriginal()
                    .add(multa)
                    .add(juros)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }

        return conta;
    }

}