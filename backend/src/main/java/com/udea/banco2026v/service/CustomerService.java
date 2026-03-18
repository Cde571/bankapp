package com.udea.banco2026v.service;

import com.udea.banco2026v.dto.CustomerDTO;
import com.udea.banco2026v.entity.Customer;
import com.udea.banco2026v.mapper.CustomerMapper;
import com.udea.banco2026v.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toDTO).toList();
    }

    public CustomerDTO getCustomerById(Long id) {
        return customerMapper.toDTO(findEntityById(id));
    }

    public CustomerDTO createCustomer(CustomerDTO dto) {
        validateUniqueFields(dto, null);
        Customer customer = customerMapper.toEntity(dto);
        if (customer.getBalance() == null) customer.setBalance(BigDecimal.ZERO);
        return customerMapper.toDTO(customerRepository.save(customer));
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
        Customer customer = findEntityById(id);
        validateUniqueFields(dto, id);
        customerMapper.updateEntity(customer, dto);
        return customerMapper.toDTO(customerRepository.save(customer));
    }

    public void deleteCustomer(Long id) {
        customerRepository.delete(findEntityById(id));
    }

    public Customer findEntityById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con id: " + id));
    }

    private void validateUniqueFields(CustomerDTO dto, Long currentId) {
        boolean repeatedAccount = currentId == null
                ? customerRepository.existsByAccountNumber(dto.getAccountNumber())
                : customerRepository.existsByAccountNumberAndIdNot(dto.getAccountNumber(), currentId);
        if (repeatedAccount) throw new IllegalArgumentException("El número de cuenta ya existe");

        boolean repeatedEmail = currentId == null
                ? customerRepository.existsByEmail(dto.getEmail())
                : customerRepository.existsByEmailAndIdNot(dto.getEmail(), currentId);
        if (repeatedEmail) throw new IllegalArgumentException("El correo ya existe");
    }
}
