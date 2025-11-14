package com.example.demo.user.mapper;

import com.example.demo.user.entity.User;
import com.example.demo.user.dto.UserCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserCreateMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(encodePassword(userCreateDto.getPassword()))")
    public abstract User toUser(UserCreateDto userCreateDto);

    protected String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}