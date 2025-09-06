package com.example.demo.exception;

import com.example.demo.constant.ErrorMessage;
import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public DuplicateResourceException(ErrorMessage errorMessage) {
        super(errorMessage.getMessageRu());
        this.errorMessage = errorMessage;
    }
}