package com.udea.banco2026v.controller;

import com.udea.banco2026v.dto.TransactionDTO;
import com.udea.banco2026v.dto.TransferRequestDTO;
import com.udea.banco2026v.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/customer/{customerId}")
    public List<TransactionDTO> getTransactionsByCustomer(@PathVariable Long customerId) {
        return transactionService.getTransactionsByCustomer(customerId);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDTO transfer(@Valid @RequestBody TransferRequestDTO request) {
        return transactionService.transfer(request);
    }
}
