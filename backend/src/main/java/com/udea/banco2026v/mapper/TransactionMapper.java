package com.udea.banco2026v.mapper;

import com.udea.banco2026v.dto.TransactionDTO;
import com.udea.banco2026v.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) return null;
        return new TransactionDTO(
                transaction.getId(),
                transaction.getSourceCustomer().getId(),
                transaction.getSourceCustomer().getFullName(),
                transaction.getTargetCustomer().getId(),
                transaction.getTargetCustomer().getFullName(),
                transaction.getAmount(),
                transaction.getCreatedAt(),
                transaction.getType(),
                transaction.getDescription()
        );
    }
}
