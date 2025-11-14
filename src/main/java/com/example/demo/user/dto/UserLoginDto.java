package com.example.demo.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Schema(description = "Данные для авторизации пользователя")
@Builder
@AllArgsConstructor
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