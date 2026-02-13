package com.gabrielsantana.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(
    Long id,
    UUID userId,

    String title,
    String description,

    LocalDateTime createdAt,
    LocalDateTime startAt,
    LocalDateTime endAt,

    TaskPriority priority,
    TaskStatus status
) {
}
