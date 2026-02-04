package com.gabrielsantana.todolist.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskRequestDTO(
        @NotNull
        UUID userId,

        @NotBlank
        @Size(max = 75)
        String title,

        String description,

        LocalDateTime startAt,
        LocalDateTime endAt,

        @NotNull
        TaskPriority priority
) {
}
