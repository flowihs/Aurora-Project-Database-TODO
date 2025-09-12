package com.example.demo.user.controller;

import com.example.demo.auth.controller.AuthController;
import com.example.demo.security.jwt.JwtAuthenticationFilter;
import com.example.demo.user.dto.JwtResponse;
import com.example.demo.user.dto.UserRegistrationResponseDto;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void registrationUserTest() throws Exception {
        when(userService.register(any())).thenReturn(
                UserRegistrationResponseDto.builder()
                        .success(true)
                        .message("Пользователь успешно зарегистрирован")
                        .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                        .userId(1L)
                        .nickname("ivan_petrov")
                        .build()
        );

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nickname": "testUser1",
                                  "password": "password123",
                                  "firstname": "Ivan",
                                  "lastname": "Ivanov",
                                  "email": "test@mail.com"
                                }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void loginUserTest() throws Exception {
        when(userService.login(any())).thenReturn(
                new JwtResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwibmlja25hbWUiOiJpdmFuX3BldHJvdiJ9")
        );

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nickname": "ivan_petrov",
                                  "password": "password123"
                                }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwibmlja25hbWUiOiJpdmFuX3BldHJvdiJ9"));
    }
}