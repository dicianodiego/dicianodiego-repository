package com.fabrick.restclient.account.dto;

public record Amount(Double debtorAmount, Double debtorCurrency, Double creditorAmount,
                     String creditorCurrency, String creditorCurrencyDate, Integer exchangeRate) {
}
