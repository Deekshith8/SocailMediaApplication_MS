package com.Deekshith.Project.ApiGateway.Exceptions;

public class NotValidTokenException extends  Exception{


    private  String message ;

    public  NotValidTokenException(String message){
         super(message);
         this.message = message;
    }

}
