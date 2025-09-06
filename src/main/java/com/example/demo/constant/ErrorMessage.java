package com.example.demo.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorMessage {

    // User exceptions
    ERROR_1001("ERROR_1001", "Никнейм уже занят", "Nickname already taken"),
    ERROR_1002("ERROR_1002", "Email уже зарегистрирован", "Email already registered"),
    ERROR_1003("ERROR_1003", "Пользователь не найден", "User not found"),
    ERROR_1004("ERROR_1004", "Неверный пароль", "Invalid password"),

    // Technical exceptions
    ERROR_2001("ERROR_2001", "Внутренняя ошибка сервера", "Internal server error"),

    // Validation exceptions
    ERROR_3001("ERROR_3001", "Невалидные данные", "Invalid data");

    private final String code;
    private final String messageRu;
    private final String messageEn;

    @JsonCreator
    ErrorMessage(final String code, final String messageRu, final String messageEn) {
        this.code = code;
        this.messageRu = messageRu;
        this.messageEn = messageEn;
    }
}
