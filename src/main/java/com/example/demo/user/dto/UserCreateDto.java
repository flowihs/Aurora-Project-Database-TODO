package com.example.demo.user.dto;

import lombok.Value;

@Value
public class UserCreateDto {
    String nickname;
    String firstName;
    String lastName;
    String password;
}
