package com.pessoasapi.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private String message;

    public BusinessException(String message) {
        this.message = message;
    }

}
