package com.example.demo.note.service;

import com.example.demo.note.dto.CreateNoteRequest;
import com.example.demo.note.dto.NoteResponse;
import com.example.demo.user.dto.UserCreateDto;
import com.example.demo.user.dto.UserRegistrationResponseDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@Transactional
class NoteServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Test
    void getUserNotes() {
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .nickname("flowihs")
                .firstname("Иван")
                .lastname("Петров")
                .email("ivan.petrov@example.com")
                .password("SecurePassword123!")
                .build();

        UserRegistrationResponseDto userReg = userService.register(userCreateDto);
        User user = userService.findById(userReg.getUserId());

        List<NoteResponse> noteResponseList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            noteResponseList.add(noteService.createNote(user, CreateNoteRequest.builder()
                    .name("Мои планы %s".formatted(i))
                    .content("%s".formatted(i))
                    .build()));
        }

        List<NoteResponse> userNotes = noteService.getUserNotes(user);

        int counter = 0;
        for (NoteResponse note : noteResponseList) {
            for (NoteResponse userNote : userNotes) {
                if (Objects.equals(note.getId(), userNote.getId()) && Objects.equals(note.getName(), userNote.getName()) && Objects.equals(note.getContent(), userNote.getContent())) {
                    counter++;
                }
            }
        }
        Assertions.assertEquals(noteResponseList.size(), counter);

    }
}