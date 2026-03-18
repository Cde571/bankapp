package com.udea.banco2026v.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class CustomerDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String fullName;

    @NotBlank(message = "El número de cuenta es obligatorio")
    private String accountNumber;

    @DecimalMin(value = "0.0", inclusive = true, message = "El saldo no puede ser negativo")
    private BigDecimal balance;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no es válido")
    private String email;

    public CustomerDTO() {}

    public CustomerDTO(Long id, String fullName, String accountNumber, BigDecimal balance, String email) {
        this.id = id;
        this.fullName = fullName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
