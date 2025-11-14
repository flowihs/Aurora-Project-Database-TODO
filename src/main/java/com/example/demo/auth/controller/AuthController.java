package com.example.demo.auth.controller;

import com.example.demo.user.dto.JwtResponse;
import com.example.demo.user.dto.UserCreateDto;
import com.example.demo.user.dto.UserLoginDto;
import com.example.demo.user.dto.UserRegistrationResponseDto;
import com.example.demo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "API для регистрации и авторизации пользователей")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Создает нового пользователя в системе. Проверяет уникальность nickname и email."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно зарегистрирован",
                    content = @Content(schema = @Schema(implementation = UserRegistrationResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Конфликт: nickname или email уже существуют",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные входные данные",
                    content = @Content
            )
    })
    public ResponseEntity<UserRegistrationResponseDto> register(@RequestBody final UserCreateDto dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Авторизация пользователя",
            description = "Аутентификация пользователя по nickname и паролю. Возвращает JWT токен для доступа к защищенным ресурсам."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная авторизация",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Неверные учетные данные: пользователь не найден или неверный пароль",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные входные данные",
                    content = @Content
            )
    })
    public JwtResponse login(@RequestBody final UserLoginDto dto) {
        return userService.login(dto);
    }
}