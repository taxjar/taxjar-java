package com.taxjar.exception;

public class ApiConnectionException extends TaxjarException {
    public ApiConnectionException(String errorMessage) {
        this(errorMessage, null);
    }

    public ApiConnectionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
