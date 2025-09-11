package com.example.demo.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
@Schema(description = "Данные для авторизации пользователя")
public class UserLoginDto {

    @Schema(
            description = "Имя пользователя (никнейм)",
            example = "ivan_petrov"
    )
    String nickname;

    @Schema(
            description = "Пароль пользователя",
            example = "SecurePassword123!"
    )
    String password;
}