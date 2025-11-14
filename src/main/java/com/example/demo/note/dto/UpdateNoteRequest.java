package com.example.demo.note.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Schema(description = "Данные для обновления заметки")
public class UpdateNoteRequest {

    @Schema(description = "Заголовок заметки", example = "Обновленные планы")
    String name;

    @Schema(description = "Содержимое заметки", example = "1. Новый пункт плана")
    String content;
}