package com.example.demo.user.service;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.jwt.JwtService;
import com.example.demo.user.dto.UserRegistrationResponseDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.dto.JwtResponse;
import com.example.demo.user.dto.UserCreateDto;
import com.example.demo.user.dto.UserLoginDto;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    public UserRegistrationResponseDto register(final UserCreateDto dto) {
        if (userRepository.existsByNickname(dto.getNickname())) {
            throw new DuplicateResourceException("Nickname уже зарегистрирован");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email уже зарегистрирован");
        }

        User user = User.builder()
                .nickname(dto.getNickname())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(savedUser.getNickname());
        String token = jwtService.generateToken(userDetails);

        return UserRegistrationResponseDto.builder()
                .success(true)
                .message("Пользователь успешно зарегистрирован")
                .token(token)
                .userId(savedUser.getId())
                .nickname(savedUser.getNickname())
                .build();
    }

    @Transactional
    public JwtResponse login(final UserLoginDto dto) {
        User user = userRepository.findByNickname(dto.getNickname())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Неверный пароль");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getNickname());
        String token = jwtService.generateToken(userDetails);

        return new JwtResponse(token);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}