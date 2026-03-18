package com.udea.banco2026v.service;

import com.udea.banco2026v.dto.TransactionDTO;
import com.udea.banco2026v.dto.TransferRequestDTO;
import com.udea.banco2026v.entity.Customer;
import com.udea.banco2026v.entity.Transaction;
import com.udea.banco2026v.mapper.TransactionMapper;
import com.udea.banco2026v.repository.CustomerRepository;
import com.udea.banco2026v.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository,
                              CustomerRepository customerRepository,
                              CustomerService customerService,
                              TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.transactionMapper = transactionMapper;
    }

    public List<TransactionDTO> getTransactionsByCustomer(Long customerId) {
        customerService.findEntityById(customerId);
        return transactionRepository.findBySourceCustomerIdOrTargetCustomerIdOrderByCreatedAtDesc(customerId, customerId)
                .stream()
                .map(transactionMapper::toDTO)
                .toList();
    }

    @Transactional
    public TransactionDTO transfer(TransferRequestDTO request) {
        if (request.getSourceCustomerId().equals(request.getTargetCustomerId())) {
            throw new IllegalArgumentException("La cuenta origen y destino no pueden ser la misma");
        }

        Customer sourceCustomer = customerService.findEntityById(request.getSourceCustomerId());
        Customer targetCustomer = customerService.findEntityById(request.getTargetCustomerId());
        BigDecimal amount = request.getAmount();

        if (sourceCustomer.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la transferencia");
        }

        sourceCustomer.setBalance(sourceCustomer.getBalance().subtract(amount));
        targetCustomer.setBalance(targetCustomer.getBalance().add(amount));

        customerRepository.save(sourceCustomer);
        customerRepository.save(targetCustomer);

        Transaction transaction = new Transaction();
        transaction.setSourceCustomer(sourceCustomer);
        transaction.setTargetCustomer(targetCustomer);
        transaction.setAmount(amount);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setType("TRANSFER");
        transaction.setDescription(
                request.getDescription() == null || request.getDescription().isBlank()
                        ? "Transferencia entre cuentas"
                        : request.getDescription()
        );

        return transactionMapper.toDTO(transactionRepository.save(transaction));
    }
}
