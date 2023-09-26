package com.pessoasapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handlingException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getFieldError().getDefaultMessage()));
    }

}
