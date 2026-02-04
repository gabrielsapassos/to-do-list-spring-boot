package com.gabrielsantana.todolist.user;

import java.util.UUID;

public record UserRequestDTO(
        String username,
        String name,
        String password
) {
}
