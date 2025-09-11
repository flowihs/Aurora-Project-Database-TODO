package com.example.demo.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Schema(description = "Данные для регистрации нового пользователя")
public class UserCreateDto {

    @Schema(
            description = "Уникальное имя пользователя (никнейм)",
            example = "flowihs"
    )
    String nickname;

    @Schema(
            description = "Имя пользователя",
            example = "Иван"
    )
    String firstname;

    @Schema(
            description = "Фамилия пользователя",
            example = "Петров"
    )
    String lastname;

    @Schema(
            description = "Электронная почта пользователя",
            example = "ivan.petrov@example.com"
    )
    String email;

    @Schema(
            description = "Пароль пользователя",
            example = "SecurePassword123!"
    )
    String password;
}