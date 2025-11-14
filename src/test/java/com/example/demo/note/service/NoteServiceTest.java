package com.example.demo.note.service;

import com.example.demo.note.dto.CreateNoteRequest;
import com.example.demo.note.dto.NoteResponse;
import com.example.demo.note.dto.UpdateNoteRequest;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private final User user = User.builder()
            .nickname("flowihs")
            .firstname("Иван")
            .lastname("Петров")
            .email("ivan.petrov@example.com")
            .password("SecurePassword123!")
            .build();

    private final List<Note> mockNotes = List.of(
            Note.builder()
                    .id(1L)
                    .name("Мои планы 1")
                    .content("1")
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .build(),
            Note.builder()
                    .id(2L)
                    .name("Мои планы 2")
                    .content("2")
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .build(),
            Note.builder()
                    .id(3L)
                    .name("Мои дела")
                    .content("2")
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .build()
    );


    @Test
    void getUserNotesTest() {
        when(noteRepository.findByUser(user)).thenReturn(mockNotes);

        List<NoteResponse> result = noteService.getUserNotes(user);

        List<NoteResponse> noteResponses = mockNotes.stream().map(NoteResponse::fromEntity).toList();

        assertThat(result).hasSize(3);
        assertThat(result).isEqualTo(noteResponses);
    }

    @Test
    void getNotesWithLikeFilterTest() {
        when(noteRepository.findAllByUserAndNameLike(user, "планы")).thenReturn(List.of(
                Note.builder()
                        .id(mockNotes.get(0).getId())
                        .name(mockNotes.get(0).getName())
                        .content(mockNotes.get(0).getContent())
                        .createdAt(mockNotes.get(0).getCreatedAt())
                        .user(user)
                        .build(),
                Note.builder()
                        .id(mockNotes.get(1).getId())
                        .name(mockNotes.get(1).getName())
                        .content(mockNotes.get(1).getContent())
                        .createdAt(mockNotes.get(1).getCreatedAt())
                        .user(user)
                        .build()));

        List<NoteResponse> result = noteService.getNotesWithLikeFilter(user, "планы");

        List<NoteResponse> noteResponses = List.of(
                NoteResponse.fromEntity(mockNotes.get(0)),
                NoteResponse.fromEntity(mockNotes.get(1))
        );

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(noteResponses);
    }

    @Test
    void CreateNoteTest() {
        CreateNoteRequest request = CreateNoteRequest.builder()
                .name("Имя")
                .content("Контент")
                .build();

        Note newSavedNote = Note.builder()
                .name(request.getName())
                .content(request.getContent())
                .user(user)
                .build();

        when(noteRepository.save(any(Note.class))).thenReturn(newSavedNote);

        NoteResponse result = noteService.createNote(user, request);

        assertThat(result).isEqualTo(NoteResponse.fromEntity(newSavedNote));

        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void updateNoteTest() {
        UpdateNoteRequest request = UpdateNoteRequest.builder()
                .name("Новое имя")
                .content("Новый контент")
                .build();

        when(noteRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(mockNotes.get(0)));

        Note expectedSavedNote = Note.builder()
                .id(mockNotes.get(0).getId())
                .user(user)
                .name(request.getName())
                .content(request.getContent())
                .build();

        when(noteRepository.save(any(Note.class))).thenReturn(expectedSavedNote);

        NoteResponse result = noteService.updateNote(1L, user, request);
        assertThat(result).isEqualTo(NoteResponse.fromEntity(expectedSavedNote));

        verify(noteRepository, times(1)).findByIdAndUser(1L, user);
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void deleteNoteTest() {
        when(noteRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(mockNotes.get(0)));

        noteService.deleteNote(1L, user);

        verify(noteRepository, times(1)).findByIdAndUser(1L, user);
        verify(noteRepository, times(1)).delete(mockNotes.get(0));
    }

    @Test
    void getNoteByIdTest() {
        when(noteRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(mockNotes.get(0)));

        Note result = noteService.getNoteById(1L, user);

        assertThat(result).isEqualTo(mockNotes.get(0));
    }

    @Test
    void getUserNotesResponseTest() {
        when(noteRepository.findByUser(user)).thenReturn(mockNotes);

        List<NoteResponse> result = noteService.getUserNotesResponse(user);

        List<NoteResponse> noteResponses = mockNotes.stream().map(NoteResponse::fromEntity).toList();

        assertThat(result).hasSize(3);
        assertThat(result).isEqualTo(noteResponses);
    }

    @Test
    void getNoteResponseByIdTest() {
        when(noteRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(mockNotes.get(0)));

        NoteResponse result = noteService.getNoteResponseById(1L, user);

        assertThat(result).isEqualTo(NoteResponse.fromEntity(mockNotes.get(0)));
    }

}