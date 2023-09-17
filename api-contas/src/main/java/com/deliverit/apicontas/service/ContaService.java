package com.deliverit.apicontas.service;

import com.deliverit.apicontas.enums.Status;
import com.deliverit.apicontas.model.Conta;
import com.deliverit.apicontas.model.multa.CorrecaoPorAtraso;
import com.deliverit.apicontas.model.multa.MultaPorAtrasoMinimo;
import com.deliverit.apicontas.model.multa.MultaPorAtrasoSuperiorCincoDias;
import com.deliverit.apicontas.model.multa.MultaPorAtrasoSuperiorTresDias;
import com.deliverit.apicontas.repository.ContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Conta salvar(Conta conta) throws Exception {
        Optional<Conta> hasContaExistenteComMesmoNome =
                contaRepository.findByNomeAndDataVencimentoAndAtivo(conta.getNome(),
                        conta.getDataVencimento(),
                        conta.getAtivo());
        if(hasContaExistenteComMesmoNome.isPresent() &&
                conta.getId() != hasContaExistenteComMesmoNome.get().getId()) {
            throw new Exception();
        }
        conta.setDataPagamento(Status.PAGO.equals(conta.getStatus()) ? LocalDateTime.now() : null);
        Conta contaComCorrecao = this.contaEmAtrasoAplicaCorrecao(conta);
        return contaRepository.save(contaComCorrecao);
    }

    public Optional<Conta> buscarPorId(Long id) {
        Optional<Conta> conta = contaRepository.findById(id);
        if (!conta.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(this.aplicarCorrecao(conta.get()));
    }

    public Optional<Conta> buscarContaPendente(Long id) {
        Optional<Conta> conta = contaRepository.findById(id);
        if (!conta.isPresent() ||
                conta.get().getStatus().equals(Status.PAGO)) {
            return Optional.empty();
        }
        return Optional.of(this.contaEmAtrasoAplicaCorrecao(conta.get()));
    }

    public List<Conta> listaContasComCorrecao() {
        return contaRepository.findAll(Sort.by(
                Sort.Direction.ASC,
                "nome"))
                .stream()
                .map(this::aplicarCorrecao)
                .collect(Collectors.toList());
    }

    private Conta contaEmAtrasoAplicaCorrecao(Conta conta) {
            Long quantidadeDiasEmAtraso = conta.getDataVencimento().until(LocalDate.now(), ChronoUnit.DAYS);
            BigDecimal juros = BigDecimal.ZERO;
            BigDecimal multa = BigDecimal.ZERO;

            if (quantidadeDiasEmAtraso > 0 && quantidadeDiasEmAtraso <= 3) {
                multa = conta.getValorOriginal().multiply(BigDecimal.valueOf(0.02));
                juros = BigDecimal.valueOf(0.001).multiply(BigDecimal.valueOf(quantidadeDiasEmAtraso));

            } else if (quantidadeDiasEmAtraso > 3 && quantidadeDiasEmAtraso <= 5) {
                multa = conta.getValorOriginal().multiply(BigDecimal.valueOf(0.03));
                juros = BigDecimal.valueOf(0.002).multiply(BigDecimal.valueOf(quantidadeDiasEmAtraso));


            } else if (quantidadeDiasEmAtraso > 5) {
                multa = conta.getValorOriginal().multiply(BigDecimal.valueOf(0.05));
                juros = BigDecimal.valueOf(0.003).multiply(BigDecimal.valueOf(quantidadeDiasEmAtraso));
            }

            conta.setDiasAtrasado(quantidadeDiasEmAtraso < 0 ? 0 : quantidadeDiasEmAtraso);
            conta.setValorCorrigido(conta.getValorOriginal()
                    .add(multa)
                    .add(juros)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));

        return conta;
    }

    private Conta aplicarCorrecao(Conta conta) {
        CorrecaoPorAtraso correcaoPorAtraso = new CorrecaoPorAtraso();
        Long quantidadeDiasEmAtraso = conta.getDataVencimento().until(LocalDate.now(), ChronoUnit.DAYS);

        correcaoPorAtraso.calcular(new MultaPorAtrasoMinimo(), conta, quantidadeDiasEmAtraso);
        correcaoPorAtraso.calcular(new MultaPorAtrasoSuperiorTresDias(), conta, quantidadeDiasEmAtraso);
        correcaoPorAtraso.calcular(new MultaPorAtrasoSuperiorCincoDias(), conta, quantidadeDiasEmAtraso);

        return conta;
    }
}
