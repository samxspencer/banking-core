package com.samxspencer.bankingcore.repository;

import com.samxspencer.bankingcore.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // List<Transaction> findByAccountNumber(String accountNumber);
    Page<Transaction> findByAccountNumber(String accountNumber, Pageable pageable);
    
}