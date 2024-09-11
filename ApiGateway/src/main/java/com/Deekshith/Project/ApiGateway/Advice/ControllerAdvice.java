package com.Deekshith.Project.ApiGateway.Advice;


import com.Deekshith.Project.ApiGateway.Exceptions.NotValidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {


   @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({NotValidTokenException.class})
    public Map<HttpStatus, String> notValidTokenException(NotValidTokenException ex){
        Map<HttpStatus , String> returnMap = new HashMap<>();
        returnMap.put(HttpStatus.FORBIDDEN, ex.getMessage());

        return  returnMap;
    }

}
