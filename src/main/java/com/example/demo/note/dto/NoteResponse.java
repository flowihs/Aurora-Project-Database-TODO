package com.example.demo.note.dto;

import com.example.demo.note.entity.Note;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@Schema(description = "Данные заметки")
public class NoteResponse {

    @Schema(description = "ID заметки", example = "1")
    Long id;

    @Schema(description = "Название заметки", example = "Мои планы на выходные")
    String name;

    @Schema(description = "Содержимое заметки", example = "1. Сходить в магазин\n2. Почитать книгу")
    String content;

    @Schema(description = "Дата создания заметки", example = "2024-01-15T10:30:00")
    LocalDateTime createdAt;

    @Schema(description = "Дата последнего обновления заметки", example = "2024-01-15T14:45:00")
    LocalDateTime updatedAt;

    public static NoteResponse fromEntity(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .name(note.getName())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }
}