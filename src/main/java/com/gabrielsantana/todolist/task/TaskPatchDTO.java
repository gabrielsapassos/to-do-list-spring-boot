package com.gabrielsantana.todolist.task;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskPatchDTO(
        @Size(max = 75)
        String title,
        @Size(max = 500)
        String description,

        LocalDateTime startAt,
        LocalDateTime endAt,

        TaskPriority priority,
        TaskStatus status
) {
}
