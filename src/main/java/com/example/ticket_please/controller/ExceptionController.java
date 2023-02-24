package com.example.ticket_please.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class ExceptionController {

    private final Log log = LogFactory.getLog(ExceptionController.class);

    @ExceptionHandler(Exception.class)
    public String handleException(final HttpServletRequest request, final Exception exception) {
        log.error("Error", exception);
        System.out.println(exception.getMessage());
        return "error";
    }
}