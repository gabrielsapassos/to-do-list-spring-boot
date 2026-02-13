package com.gabrielsantana.todolist.task;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskUpdateDTO(
        @Size(min = 3, message = "Title must have at least 3 digits")
        String title,

        String description,

        LocalDateTime startAt,
        LocalDateTime endAt,

        TaskPriority priority,
        TaskStatus status
) {
}
