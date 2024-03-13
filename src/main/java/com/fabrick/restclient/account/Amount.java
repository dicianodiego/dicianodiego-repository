package com.fabrick.restclient.account;

public record Amount(Double debtorAmount, Double debtorCurrency, Double creditorAmount,
                     String creditorCurrency, String creditorCurrencyDate, Integer exchangeRate) {
}
