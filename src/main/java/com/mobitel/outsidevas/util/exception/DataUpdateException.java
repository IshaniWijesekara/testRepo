package com.mobitel.outsidevas.util.exception;

public class DataUpdateException extends Exception {
    public DataUpdateException() {
    }

    public DataUpdateException(String message) {
        super(message);
    }

    public DataUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataUpdateException(Throwable cause) {
        super(cause);
    }

    public DataUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
