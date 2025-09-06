package com.example.demo.exception;

import com.example.demo.constant.ErrorMessage;
import lombok.Getter;

@Getter
public class InvalidCredentialsException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public InvalidCredentialsException() {
        super(ErrorMessage.ERROR_1004.getMessageRu());
        this.errorMessage = ErrorMessage.ERROR_1004;
    }
}