package com.fabrick.restclient.account;

public record Balance(String status, String[] error, Payload payload) {
}
