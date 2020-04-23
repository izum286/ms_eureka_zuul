package com.izum286.search.Exceptions;

public class ConflictServiceException extends ServiceException {
    public ConflictServiceException(String message) {
        super(message);
    }

    public ConflictServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
