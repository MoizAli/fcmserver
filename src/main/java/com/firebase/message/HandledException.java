package com.firebase.message;

public abstract class HandledException extends RuntimeException {

    public HandledException(String message) {
        super(message);
    }

    public HandledException(String message, Throwable t) {
        super(message, t);
    }

    public abstract ErrorCode getErrorCode();
}
