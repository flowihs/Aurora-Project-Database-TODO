package com.example.demo.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException() {
        super("Заметка не найдена");
    }
}
