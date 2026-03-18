package com.udea.banco2026v.mapper;

import com.udea.banco2026v.dto.CustomerDTO;
import com.udea.banco2026v.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) return null;
        return new CustomerDTO(
                customer.getId(),
                customer.getFullName(),
                customer.getAccountNumber(),
                customer.getBalance(),
                customer.getEmail()
        );
    }

    public Customer toEntity(CustomerDTO dto) {
        if (dto == null) return null;
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setFullName(dto.getFullName());
        customer.setAccountNumber(dto.getAccountNumber());
        customer.setBalance(dto.getBalance());
        customer.setEmail(dto.getEmail());
        return customer;
    }

    public void updateEntity(Customer customer, CustomerDTO dto) {
        customer.setFullName(dto.getFullName());
        customer.setAccountNumber(dto.getAccountNumber());
        customer.setBalance(dto.getBalance());
        customer.setEmail(dto.getEmail());
    }
}
