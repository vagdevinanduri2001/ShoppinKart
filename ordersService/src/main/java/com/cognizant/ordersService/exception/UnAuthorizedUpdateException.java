package com.cognizant.ordersService.exception;

public class UnAuthorizedUpdateException extends RuntimeException{
    public UnAuthorizedUpdateException() {
    }

    public UnAuthorizedUpdateException(String message) {
        super(message);
    }
}
