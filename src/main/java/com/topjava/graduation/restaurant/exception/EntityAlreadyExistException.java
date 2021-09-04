package com.topjava.graduation.restaurant.exception;

public class EntityAlreadyExistException extends RuntimeException{
    public EntityAlreadyExistException() {
    }

    public EntityAlreadyExistException(String message) {
        super(message);
    }

    public EntityAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
