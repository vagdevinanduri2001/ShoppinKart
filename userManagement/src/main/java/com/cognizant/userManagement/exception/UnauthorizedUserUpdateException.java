package com.cognizant.userManagement.exception;

public class UnauthorizedUserUpdateException extends RuntimeException{

    public UnauthorizedUserUpdateException(){super();}
    public UnauthorizedUserUpdateException(String message){
        super(message);
    }

}
