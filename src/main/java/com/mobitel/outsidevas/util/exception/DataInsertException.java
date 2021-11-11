package com.mobitel.outsidevas.util.exception;

public class DataInsertException extends Exception {
    public DataInsertException() {
    }

    public DataInsertException(String message) {
        super(message);
    }

    public DataInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataInsertException(Throwable cause) {
        super(cause);
    }

    public DataInsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
