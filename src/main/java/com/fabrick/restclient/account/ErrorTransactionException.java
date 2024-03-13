package com.fabrick.restclient.account;

public class ErrorTransactionException extends RuntimeException  {
    public ErrorTransactionException (String msg) {

        super(msg);
    }
}
