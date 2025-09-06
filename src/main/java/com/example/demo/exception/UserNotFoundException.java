package com.example.demo.exception;

import com.example.demo.constant.ErrorMessage;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public UserNotFoundException() {
        super(ErrorMessage.ERROR_1003.getMessageRu());
        this.errorMessage = ErrorMessage.ERROR_1003;
    }
}