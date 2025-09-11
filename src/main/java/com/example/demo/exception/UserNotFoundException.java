package com.example.demo.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Пользователь не был найден");
    }
}