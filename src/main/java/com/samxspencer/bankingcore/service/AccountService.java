package com.samxspencer.bankingcore.service;

import com.samxspencer.bankingcore.domain.Account;
import com.samxspencer.bankingcore.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found"));
    }

    private String generateAccountNumber() {
        return UUID.randomUUID().toString();
    }
}