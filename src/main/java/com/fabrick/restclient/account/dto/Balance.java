package com.fabrick.restclient.account.dto;

public record Balance(String status, String[] error, Payload payload) {
}
