package com.fabrick.restclient.account.dto;

public record MoneyTransfertInput(Creditor creditor, String executionDate,
                                  String uri, String description, Double amount,
                                  String currency, Boolean isUrgent, Boolean isInstant,
                                  String feeType, String feeAccountId, TaxRelief taxRelief ) {
}
