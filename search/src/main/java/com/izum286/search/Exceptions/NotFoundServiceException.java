package com.izum286.search.Exceptions;

public class NotFoundServiceException extends ServiceException{
    public NotFoundServiceException(String message) {
        super(message);
    }

    public NotFoundServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
