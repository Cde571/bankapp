package com.udea.banco2026v.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private Long sourceCustomerId;
    private String sourceCustomerName;
    private Long targetCustomerId;
    private String targetCustomerName;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private String type;
    private String description;

    public TransactionDTO() {}

    public TransactionDTO(Long id, Long sourceCustomerId, String sourceCustomerName,
                          Long targetCustomerId, String targetCustomerName, BigDecimal amount,
                          LocalDateTime createdAt, String type, String description) {
        this.id = id;
        this.sourceCustomerId = sourceCustomerId;
        this.sourceCustomerName = sourceCustomerName;
        this.targetCustomerId = targetCustomerId;
        this.targetCustomerName = targetCustomerName;
        this.amount = amount;
        this.createdAt = createdAt;
        this.type = type;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSourceCustomerId() { return sourceCustomerId; }
    public void setSourceCustomerId(Long sourceCustomerId) { this.sourceCustomerId = sourceCustomerId; }
    public String getSourceCustomerName() { return sourceCustomerName; }
    public void setSourceCustomerName(String sourceCustomerName) { this.sourceCustomerName = sourceCustomerName; }
    public Long getTargetCustomerId() { return targetCustomerId; }
    public void setTargetCustomerId(Long targetCustomerId) { this.targetCustomerId = targetCustomerId; }
    public String getTargetCustomerName() { return targetCustomerName; }
    public void setTargetCustomerName(String targetCustomerName) { this.targetCustomerName = targetCustomerName; }
    public java.math.BigDecimal getAmount() { return amount; }
    public void setAmount(java.math.BigDecimal amount) { this.amount = amount; }
    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
