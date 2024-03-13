package com.fabrick.restclient.account.dto;

public class ErrorTransactionException extends RuntimeException  {
    public ErrorTransactionException (String msg) {

        super(msg);
    }
}
