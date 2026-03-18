package com.udea.banco2026v.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferRequestDTO {

    @NotNull(message = "El cliente origen es obligatorio")
    private Long sourceCustomerId;

    @NotNull(message = "El cliente destino es obligatorio")
    private Long targetCustomerId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", inclusive = true, message = "El monto debe ser mayor que cero")
    private BigDecimal amount;

    private String description;

    public TransferRequestDTO() {}

    public TransferRequestDTO(Long sourceCustomerId, Long targetCustomerId, BigDecimal amount, String description) {
        this.sourceCustomerId = sourceCustomerId;
        this.targetCustomerId = targetCustomerId;
        this.amount = amount;
        this.description = description;
    }

    public Long getSourceCustomerId() { return sourceCustomerId; }
    public void setSourceCustomerId(Long sourceCustomerId) { this.sourceCustomerId = sourceCustomerId; }
    public Long getTargetCustomerId() { return targetCustomerId; }
    public void setTargetCustomerId(Long targetCustomerId) { this.targetCustomerId = targetCustomerId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
