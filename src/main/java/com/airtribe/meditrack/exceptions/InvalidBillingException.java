package com.airtribe.meditrack.exceptions;

public class InvalidBillingException extends RuntimeException {
    public InvalidBillingException(String message) {
        super(message);
    }

    public InvalidBillingException(String message, Throwable cause) {
        super(message, cause);
    }
}
