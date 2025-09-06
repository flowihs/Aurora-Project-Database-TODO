package com.example.demo.user.dto;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class UserRegistrationResponseDto {
    private boolean success;
    private String message;
    private String token;
    private Long userId;
    private String nickname;
}