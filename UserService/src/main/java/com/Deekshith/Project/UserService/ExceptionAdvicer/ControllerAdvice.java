package com.Deekshith.Project.UserService.ExceptionAdvicer;


import com.Deekshith.Project.UserService.Exception.InvalidRoleException;
import com.Deekshith.Project.UserService.Exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String , String> userNotFoundException(UserNotFoundException ex){
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("Error", ex.getMessage());
        return exceptionMap;
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidRoleException.class)
    public Map<String , String> invalidRole(InvalidRoleException ex){
        Map<String, String> exceptionMap = new HashMap<>();
        exceptionMap.put("Error", ex.getMessage());
        return exceptionMap;
    }

}
