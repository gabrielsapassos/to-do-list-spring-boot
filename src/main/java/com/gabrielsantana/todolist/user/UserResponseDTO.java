package com.gabrielsantana.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String username,
        LocalDateTime createdAt
) {
}
