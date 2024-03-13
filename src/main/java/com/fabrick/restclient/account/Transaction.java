package com.fabrick.restclient.account;

public record Transaction(
        Long transactionId, String operationId, String accountingDate,
        String valueDate, Type type, double amount,

        String currency, String description) {
}
