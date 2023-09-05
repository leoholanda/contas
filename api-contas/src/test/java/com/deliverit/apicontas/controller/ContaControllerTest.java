package com.deliverit.apicontas.controller;

import com.deliverit.apicontas.enums.Status;
import com.deliverit.apicontas.model.Conta;
import com.deliverit.apicontas.service.ContaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ContaControllerTest {

    @InjectMocks
    private ContaController contaController;

    @Mock
    private ContaService contaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.getConta1();
        this.getConta2();
    }

    @Test
    public void quandoListarContasEntaoRetornaSucesso() {
        List<Conta> listaContas = Arrays.asList(getConta1(), getConta2());
        Mockito.when(contaService.listaContasComCorrecao()).thenReturn(listaContas);

        List<Conta> response = contaController.listaContasComCorrecao();

        assertNotNull(response);
        assertEquals(response.size(), 2);
    }

    @Test
    public void quandoBuscarPeloIdContaEntaoRetornaSucesso() {
        Mockito.when(contaService.buscarPorId(any())).thenReturn(Optional.of(this.getConta1()));

        ResponseEntity<Conta> response = contaController.buscarPorId(1L);

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(response.getBody().getNome(), getConta1().getNome());
    }

    @Test
    public void quandoBuscarPeloIdContaEntaoRetornaNaoEncontrado() {
        ResponseEntity<Conta> response = contaController.buscarPorId(any());

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void quandoAdicionarContaEntaoRetornaSucesso() {
        try {
            Mockito.when(contaService.salvar(any())).thenReturn(this.getConta1());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<Conta> response = contaController.cadastrarConta(getConta1());

        assertNotNull(response);
        assertEquals(response.getBody().getNome(), getConta1().getNome());
        assertEquals(response.getBody().getStatus(), getConta1().getStatus());
        assertEquals(response.getBody().getDataVencimento(), getConta1().getDataVencimento());
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void quandoAtualizarPagamentoEntaoRetornaSucesso() throws Exception {
        Mockito.when(contaService.salvar(any())).thenReturn(this.getConta2());
        Mockito.when(contaService.buscarContaPendente(2L)).thenReturn(Optional.of(this.getConta2()));

        ResponseEntity<Conta> response = contaController.atualizaPagamento(getConta2().getId());

        assertNotNull(response.getBody());
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    private Conta getConta1() {
        return new Conta(1L,
                        "IPTU",
                        BigDecimal.valueOf(500),
                        BigDecimal.valueOf(500),
                        LocalDate.of(2023, 9, 20),
                        null,
                        0L,
                        Status.PENDENTE,
                        true
        );
    }

    private Conta getConta2() {
        return new Conta(2L,
                        "Seguro de Vida",
                        BigDecimal.valueOf(250),
                        BigDecimal.valueOf(250),
                        LocalDate.of(2023, 9, 1),
                        null,
                        0L,
                        Status.PENDENTE,
                        true
        );
    }
}
