package br.com.vr.controller;

import br.com.vr.dto.TransactionDTO;
import br.com.vr.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/transacoes")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String newTransaction(@RequestBody @Valid final TransactionDTO transactionDTO) {

        return transactionService.newTransaction(transactionDTO);
    }
}
