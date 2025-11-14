package com.example.demo.user.service;

import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.jwt.JwtService;
import com.example.demo.user.dto.JwtResponse;
import com.example.demo.user.dto.UserCreateDto;
import com.example.demo.user.dto.UserLoginDto;
import com.example.demo.user.dto.UserRegistrationResponseDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private UserService userService;

    @Test
    void login () {
        when(
                userRepository.findByNickname("testUser1")
        ).thenReturn(Optional.of(User.builder()
                .nickname("testUser1")
                .password("password123")
                .build()));

        when(
                passwordEncoder.matches("password123", "password123")
        ).thenReturn(true);

        when(
                customUserDetailsService.loadUserByUsername("testUser1")
        ).thenReturn(User.builder()
                .nickname("testUser1")
                .password("password123")
                .build());

        when(
                jwtService.generateToken(User.builder()
                        .nickname("testUser1")
                        .password("password123")
                        .build())
        ).thenReturn("token");

        assertThat(userService.login(new UserLoginDto("testUser1", "password123")))
                .extracting(JwtResponse::getToken)
                .isEqualTo("token");
    }

    @Test
    void register() {
        UserCreateDto dto = UserCreateDto.builder()
                .nickname("testUser1")
                .firstname("firstName")
                .lastname("lastName")
                .email("test@gmail.com")
                .password("password123").build();

        User savedUser = User.builder()
                .id(1L)
                .nickname("testUser1")
                .firstname("firstName")
                .lastname("lastName")
                .email("test@gmail.com")
                .password("encodedPassword")
                .build();

        when(
                userRepository.existsByNickname("testUser1")
        ).thenReturn(false);

        when(
                userRepository.existsByEmail("test@gmail.com")
        ).thenReturn(false);

        when(
                passwordEncoder.encode("password123")
        ).thenReturn("encodedPassword");

        when(
                userRepository.save(any(User.class))
        ).thenReturn(savedUser);

        when(
                customUserDetailsService.loadUserByUsername("testUser1")
        ).thenReturn(savedUser);

        when(
                jwtService.generateToken(savedUser)
        ).thenReturn("jwtToken");

        UserRegistrationResponseDto response = userService.register(dto);

        assertThat(response)
                .extracting(
                        UserRegistrationResponseDto::isSuccess,
                        UserRegistrationResponseDto::getMessage,
                        UserRegistrationResponseDto::getToken,
                        UserRegistrationResponseDto::getUserId,
                        UserRegistrationResponseDto::getNickname
                )
                .containsExactly(
                        true,
                        "Пользователь успешно зарегистрирован",
                        "jwtToken",
                        1L,
                        "testUser1"
                );

        verify(userRepository).existsByNickname("testUser1");
        verify(userRepository).existsByEmail("test@gmail.com");
        verify(userRepository).save(any(User.class));
    }

}