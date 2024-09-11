package com.Deekshith.Project.Security.Service.Exceptions;

public class InvalidUsernameAndPasswordException extends Exception{

    private String message;

    public  InvalidUsernameAndPasswordException(String message){
        super(message);
        this.message = message;    }

}
