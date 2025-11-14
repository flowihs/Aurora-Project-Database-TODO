package com.example.demo.note.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.demo.exception.StatusNotFoundException;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusNote {
    PROGRESS(1, "В процессе"),
    COMPLETED(2, "Выполнено");

    private final int id;
    private final String ru;

    StatusNote(final int id, final String ru) {
        this.id = id;
        this.ru = ru;
    }

    public static StatusNote getById(final int id) {
        for (final StatusNote status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new StatusNotFoundException();
    }
}
