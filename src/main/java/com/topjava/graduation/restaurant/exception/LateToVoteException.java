package com.topjava.graduation.restaurant.exception;

public class LateToVoteException extends RuntimeException {

    public LateToVoteException(String message) {
        super(message);
    }

    public LateToVoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public LateToVoteException(Throwable cause) {
        super(cause);
    }


}
