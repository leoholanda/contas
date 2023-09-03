package com.deliverit.apicontas.repository;

import com.deliverit.apicontas.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByNomeAndDataVencimentoAndAtivo(String nome,
                                                        LocalDate dataVencimento,
                                                        Boolean ativo);
}
