package com.example.demo.security.jwt;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceTest {

    private final JwtService jwtService = new JwtService();
    private final UserDetails userDetails = User.builder()
            .username("user")
            .password("password")
            .roles("USER")
            .build();
    private String token;

    @BeforeEach
    void setUp() {
        token = jwtService.generateToken(userDetails);
    }

    @Test
    void extractUsernameTest() {
        assertThat(jwtService.extractUsername(token)).isEqualTo(userDetails.getUsername());
    }

    @Test
    void extractClaimTest() {
        assertThat(jwtService.extractClaim(token, Claims::getSubject)).isEqualTo(userDetails.getUsername());
    }

    @Test
    void generateTokenTest() {
        assertThat(jwtService.isTokenValid(jwtService.generateToken(userDetails), userDetails)).isTrue();
    }

    @Test
    void isTokenValidTest() {
        assertThat(jwtService.isTokenValid(token, userDetails)).isTrue();
    }
}
