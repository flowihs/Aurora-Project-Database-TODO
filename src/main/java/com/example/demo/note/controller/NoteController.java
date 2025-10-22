package com.example.demo.note.controller;

import com.example.demo.note.dto.CreateNoteRequest;
import com.example.demo.note.dto.NoteResponse;
import com.example.demo.note.dto.UpdateNoteRequest;
import com.example.demo.note.service.NoteService;
import com.example.demo.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Tag(name = "Заметки", description = "API для управления заметками пользователя")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    @Operation(
            summary = "Получить все заметки пользователя",
            description = "Возвращает список всех заметок текущего авторизованного пользователя"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Заметки успешно получены",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoteResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Пользователь не авторизован"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Доступ запрещен"
            )
    })
    public ResponseEntity<List<NoteResponse>> getUserNotes(
            @AuthenticationPrincipal final User user) {
        List<NoteResponse> notes = noteService.getUserNotesResponse(user);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<NoteResponse>> getNotesWithNameFilter (
            @AuthenticationPrincipal final User user,
            @RequestParam final String nameFilter
    ) {
        return ResponseEntity.ok(noteService.getNotesWithLikeFilter(user, nameFilter));
    }

    @PostMapping
    @Operation(
            summary = "Создать новую заметку",
            description = "Создает новую заметку для текущего авторизованного пользователя"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Заметка успешно создана",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoteResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные данные запроса"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Пользователь не авторизован"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Доступ запрещен"
            )
    })
    public ResponseEntity<NoteResponse> createNote(
            @AuthenticationPrincipal final User user,
            @RequestBody final CreateNoteRequest request) {
        NoteResponse response = noteService.createNote(user, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить существующую заметку",
            description = "Обновляет содержимое заметки по указанному ID. Заметка должна принадлежать текущему пользователю."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Заметка успешно обновлена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoteResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные данные запроса"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Пользователь не авторизован"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Доступ запрещен"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Заметка не найдена"
            )
    })
    public ResponseEntity<NoteResponse> updateNote(@PathVariable final Long id,
                                                   @AuthenticationPrincipal final User user,
                                                   @RequestBody final UpdateNoteRequest request) {
        NoteResponse response = noteService.updateNote(id, user, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить заметку",
            description = "Удаляет заметку по указанному ID. Заметка должна принадлежать текущему пользователю."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Заметка успешно удалена"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Пользователь не авторизован"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Доступ запрещен"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Заметка не найдена"
            )
    })
    public ResponseEntity<Void> deleteNote(@PathVariable final Long id,
                                           @AuthenticationPrincipal final User user) {
        noteService.deleteNote(id, user);
        return ResponseEntity.noContent().build();
    }
}