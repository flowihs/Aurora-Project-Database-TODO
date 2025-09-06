package com.example.demo.user.dto;

import lombok.Value;

@Value
public class UserCreateDto {
    String nickname;
    String firstname;
    String lastname;
    String email;
    String password;
}