package com.gabrielsantana.todolist.user;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String username
) {
}
