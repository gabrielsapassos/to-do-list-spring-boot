package com.gabrielsantana.todolist.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskPutDTO(
        @NotBlank
        @Size(min = 3, max = 75, message = "Title must have at least 3 digits")
        String title,

        @Size(max = 500)
        String description,

        @NotNull
        LocalDateTime startAt,
        @NotNull
        LocalDateTime endAt,

        @NotNull
        TaskPriority priority,
        @NotNull
        TaskStatus status
) {
}
