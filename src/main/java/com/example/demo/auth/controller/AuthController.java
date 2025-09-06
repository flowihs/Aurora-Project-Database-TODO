package com.example.demo.auth.controller;

import com.example.demo.user.dto.JwtResponse;
import com.example.demo.user.dto.UserCreateDto;
import com.example.demo.user.dto.UserLoginDto;
import com.example.demo.user.dto.UserRegistrationResponseDto;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponseDto> register(@RequestBody final UserCreateDto dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody final UserLoginDto dto) {
        return userService.login(dto);
    }
}