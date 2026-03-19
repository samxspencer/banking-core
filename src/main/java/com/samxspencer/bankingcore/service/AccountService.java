package com.samxspencer.bankingcore.service;

import com.samxspencer.bankingcore.domain.Account;
import com.samxspencer.bankingcore.domain.Transaction;
import com.samxspencer.bankingcore.domain.TransactionType;
import com.samxspencer.bankingcore.repository.AccountRepository;
import com.samxspencer.bankingcore.repository.TransactionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Account createAccount(String accountHolderName, String currency) {

        String accountNumber = generateAccountNumber();

        Account account = new Account(
                accountNumber,
                accountHolderName,
                currency
        );

        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();    
    }

    @Transactional(readOnly = true)
    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found"));
    }

    private String generateAccountNumber() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount){
        
        Account account = getAccount(accountNumber);
        
        account.deposit(amount);

        return account;
    }

    @Transactional
    public Account withdraw(String accountNumber, BigDecimal amount){

        Account account = getAccount(accountNumber);

        account.withdraw(amount);

        return account;
    }

    @Transactional
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {

        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }

        Account from = getAccount(fromAccountNumber);
        Account to = getAccount(toAccountNumber);

        from.withdraw(amount);
        to.deposit(amount);

        transactionRepository.save(
                new Transaction(fromAccountNumber, TransactionType.WITHDRAWAL, amount)
        );

        transactionRepository.save(
                new Transaction(toAccountNumber, TransactionType.DEPOSIT, amount)
        );
    }

    @Transactional(readOnly = true)
    public Page<Transaction> getTransactions(String accountNumber, Pageable pageable) {
        return transactionRepository.findByAccountNumber(accountNumber, pageable);
    }
}