package com.example.demo.user.mapper;

import com.example.demo.user.entity.User;
import com.example.demo.user.dto.UserCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserCreateMapper {
    @Mapping(target = "password", expression = "java(userService.encodePassword(userCreateDto.getPassword()))")
    User toUser(UserCreateDto userCreateDto);
}
