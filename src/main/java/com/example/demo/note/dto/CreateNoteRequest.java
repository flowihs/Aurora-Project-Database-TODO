package com.example.demo.note.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Schema(description = "Данные для создания новой заметки")
public class CreateNoteRequest {

    @Schema(
            description = "Заголовок заметки",
            example = "Мои планы на выходные"
    )
    String name;

    @Schema(
            description = "Содержимое заметки",
            example = "1. Сходить в магазин\n2. Почитать книгу\n3. Посмотреть фильм"
    )
    String content;
}