package com.example.demo.user.service;

import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtService;
import com.example.demo.user.entity.User;
import com.example.demo.user.mapper.UserCreateMapper;
import com.example.demo.user.dto.JwtResponse;
import com.example.demo.user.dto.UserCreateDto;
import com.example.demo.user.dto.UserLoginDto;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserCreateMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public boolean register(final UserCreateDto dto) {
        final boolean exists = userRepository.existsByNickname(dto.getNickname());
        if (!exists) {
            return false;
        }
        userRepository.saveAndFlush(mapper.toUser(dto));
        return true;
    }

    public String encodePassword(final String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public JwtResponse login(final UserLoginDto dto) {
        final User user = userRepository.findByNickname(dto.getNickname())
                .orElse(null);
        final String password = passwordEncoder.encode(dto.getPassword());
        if (user != null && user.getPassword().equals(password)) {
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getNickname());
            return new JwtResponse(jwtService.generateToken(userDetails));
        }
        return null;
    }
}
