package com.deliverit.apicontas.controller;

import com.deliverit.apicontas.enums.Status;
import com.deliverit.apicontas.model.Conta;
import com.deliverit.apicontas.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contas")
@CrossOrigin(origins = "*")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> cadastrarConta(@RequestBody @Valid Conta conta) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(contaService.salvar(conta));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id) {
        return contaService.buscarPorId(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public @ResponseBody List<Conta> listaContasComCorrecao() {
        return contaService.listaContasComCorrecao();
    }

    @PutMapping("/pago/{id}")
    public ResponseEntity<Conta> atualizaPagamento(@PathVariable Long id) {
        return contaService.buscarContaPendente(id)
                .map(conta -> {
                    try {
                        conta.setStatus(Status.PAGO);
                        Conta contaAtualizada = contaService.salvar(conta);
                        return ResponseEntity.ok().body(contaAtualizada);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
