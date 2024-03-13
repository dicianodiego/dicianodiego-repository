package com.fabrick.restclient.account;
import com.fabrick.restclient.account.Address;

public record Creditor(String name, Account account, Address address) {
}
