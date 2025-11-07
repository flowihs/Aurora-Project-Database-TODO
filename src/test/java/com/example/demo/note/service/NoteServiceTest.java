package com.example.demo.note.service;

import com.example.demo.note.dto.NoteResponse;
import com.example.demo.note.entity.Note;
import com.example.demo.note.repository.NoteRepository;
import com.example.demo.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void getUserNotesTest() {

        User user = User.builder()
                .nickname("flowihs")
                .firstname("Иван")
                .lastname("Петров")
                .email("ivan.petrov@example.com")
                .password("SecurePassword123!")
                .build();

        Note note1 = Note.builder()
                .id(1L)
                .name("Мои планы 1")
                .content("1")
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Note note2 = Note.builder()
                .id(2L)
                .name("Мои планы 2")
                .content("2")
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        when(noteRepository.findByUser(user)).thenReturn(List.of(note1, note2));

        List<NoteResponse> result = noteService.getUserNotes(user);

        NoteResponse noteResponse1 = NoteResponse.builder()
                .id(note1.getId())
                .name(note1.getName())
                .content(note1.getContent())
                .createdAt(note1.getCreatedAt()).build();

        NoteResponse noteResponse2 = NoteResponse.builder()
                .id(note2.getId())
                .name(note2.getName())
                .content(note2.getContent())
                .createdAt(note2.getCreatedAt()).build();

        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(noteResponse1);
        assertThat(result.get(1)).isEqualTo(noteResponse2);
    }
}