package com.Deekshith.Project.Security.Service.Advice;


import com.Deekshith.Project.Security.Service.Exceptions.InvalidUsernameAndPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUsernameAndPasswordException.class)
    public Map<String , String> invaliduserNameOrPassword(InvalidUsernameAndPasswordException ex){
        Map<String,String> exceptionMap = new HashMap<>();
        exceptionMap.put("Error", ex.getMessage());
        return exceptionMap;
    }

}
