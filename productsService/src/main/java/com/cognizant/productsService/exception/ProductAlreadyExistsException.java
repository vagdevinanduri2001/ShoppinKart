package com.cognizant.productsService.exception;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(){
        super();
    }
    public ProductAlreadyExistsException(String message){
        super(message);
    }
}
