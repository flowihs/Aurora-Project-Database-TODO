package com.example.demo.exception;

public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException() {
        super("Статус не был найден");
    }
}
