package com.udea.banco2026v.repository;

import com.udea.banco2026v.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceCustomerIdOrTargetCustomerIdOrderByCreatedAtDesc(Long sourceCustomerId, Long targetCustomerId);
}
