package com.samxspencer.bankingcore.dto;

public class CreateAccountRequest {

    private String name;
    private String currency;

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }
}