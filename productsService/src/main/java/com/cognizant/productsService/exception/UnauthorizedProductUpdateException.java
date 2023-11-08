package com.cognizant.productsService.exception;

public class UnauthorizedProductUpdateException extends RuntimeException {
    public UnauthorizedProductUpdateException(String message) {
        super(message);
    }
}
