package com.fabrick.restclient.account;

import com.fabrick.restclient.account.Creditor;

public record MoneyTransfertInput(Creditor creditor, String executionDate,
                                  String uri, String description, Double amount,
                                  String currency, Boolean isUrgent, Boolean isInstant,
                                  String feeType, String feeAccountId, TaxRelief taxRelief ) {
}
