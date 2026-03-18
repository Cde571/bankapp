package com.udea.banco2026v.repository;

import com.udea.banco2026v.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByAccountNumber(String accountNumber);
    boolean existsByEmail(String email);
    boolean existsByAccountNumberAndIdNot(String accountNumber, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
}
