package com.myaudiolibrary.web.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundExeption( EntityNotFoundException e){
        return e.getMessage();
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleMethodArgumentTypeMismatchExeption( MethodArgumentTypeMismatchException e){
        return "Le paramètre '" + e.getName() + "' a une valeur incorrecte :" + e.getValue() ;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleMethodArgumentTypeMismatchExeption( IllegalArgumentException e){
        return e.getMessage();
    }

}
