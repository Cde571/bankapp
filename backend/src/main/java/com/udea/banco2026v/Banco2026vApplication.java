package com.udea.banco2026v;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.udea.banco2026v.entity.Customer;
import com.udea.banco2026v.repository.CustomerRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class Banco2026vApplication {

    public static void main(String[] args) {
        SpringApplication.run(Banco2026vApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(CustomerRepository customerRepository) {
        return args -> {
            if (customerRepository.count() == 0) {
                customerRepository.save(new Customer(null, "Ana Gomez", "10024578", new BigDecimal("1250000"), "ana@banco.com"));
                customerRepository.save(new Customer(null, "Carlos Perez", "10024579", new BigDecimal("820000"), "carlos@banco.com"));
                customerRepository.save(new Customer(null, "Luisa Herrera", "10024580", new BigDecimal("2430000"), "luisa@banco.com"));
            }
        };
    }
}
