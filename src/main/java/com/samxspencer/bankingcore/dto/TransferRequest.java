package com.samxspencer.bankingcore.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferRequest {

    @NotBlank
    private String fromAccount;

    @NotBlank
    private String toAccount;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    public String getFromAccount() { return fromAccount; }
    public String getToAccount() { return toAccount; }
    public BigDecimal getAmount() { return amount; }

    public void setFromAccount(String fromAccount) { this.fromAccount = fromAccount; }
    public void setToAccount(String toAccount) { this.toAccount = toAccount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}