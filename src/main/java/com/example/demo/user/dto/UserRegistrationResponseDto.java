package com.example.demo.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Builder;
import lombok.Value;

@Value
@Getter
@Builder
@Schema(description = "Ответ на успешную регистрацию пользователя")
public class UserRegistrationResponseDto {

    @Schema(
            description = "Флаг успешности регистрации",
            example = "true"
    )
    boolean success;

    @Schema(
            description = "Сообщение о результате регистрации",
            example = "Пользователь успешно зарегистрирован"
    )
    String message;

    @Schema(
            description = "JWT токен доступа для аутентификации",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    )
    String token;

    @Schema(
            description = "Уникальный идентификатор зарегистрированного пользователя",
            example = "123"
    )
    Long userId;

    @Schema(
            description = "Nickname зарегистрированного пользователя",
            example = "ivan_petrov"
    )
    String nickname;
}