package com.Deekshith.Project.UserService.Exception;


import org.springframework.web.bind.annotation.RestControllerAdvice;


public class InvalidRoleException extends  Exception{

    public InvalidRoleException(String message) {
        super(message);
    }

}
