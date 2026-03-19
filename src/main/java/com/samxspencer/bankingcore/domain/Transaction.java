package com.samxspencer.bankingcore.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    private Instant timestamp;

    public Transaction(){};

    public Transaction(String accountNumber, TransactionType type, BigDecimal amount){
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = Instant.now();
    }

    public Long getId(){ return id; }
    public String getAccountNumber(){ return accountNumber; }
    public TransactionType getType(){return type; }
    public BigDecimal getAmount(){ return amount; }
    public Instant getTimestamp(){ return timestamp; }
    
}
