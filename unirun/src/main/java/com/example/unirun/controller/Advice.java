package com.example.unirun.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author wzh
 */
@ControllerAdvice
public class Advice {
    /**
     *
     */
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e){
        e.printStackTrace();
        return "error";
    }
}
