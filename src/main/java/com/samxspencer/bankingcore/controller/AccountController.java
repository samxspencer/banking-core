package com.samxspencer.bankingcore.controller;

import com.samxspencer.bankingcore.domain.Account;
import com.samxspencer.bankingcore.domain.Transaction;
import com.samxspencer.bankingcore.service.AccountService;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.samxspencer.bankingcore.dto.AmountRequest;
import com.samxspencer.bankingcore.dto.TransferRequest;

import jakarta.validation.Valid;


// HTTP endpoints
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(@RequestParam String name,
                                 @RequestParam String currency) {

        return accountService.createAccount(name, currency);
    }


    @PostMapping("/{accountNumber}/deposit")
    public Account deposit(@PathVariable String accountNumber,
                        @RequestBody @Valid AmountRequest request) {

        return accountService.deposit(accountNumber, request.getAmount());
    }

    @PostMapping("/{accountNumber}/withdraw")
    public Account withdraw(@PathVariable String accountNumber,
                            @RequestBody @Valid AmountRequest request) {

        return accountService.withdraw(accountNumber, request.getAmount());
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody @Valid TransferRequest request){
        accountService.transfer(
            request.getFromAccount(),
            request.getToAccount(),
            request.getAmount()
        );
    }

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }
    
    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {

        return accountService.getAccount(accountNumber);
    }

    @GetMapping("/{accountNumber}/transactions")
    public Page<Transaction> getTransactions(
            @PathVariable String accountNumber,
            Pageable pageable) {

        return accountService.getTransactions(accountNumber, pageable);
    }
}