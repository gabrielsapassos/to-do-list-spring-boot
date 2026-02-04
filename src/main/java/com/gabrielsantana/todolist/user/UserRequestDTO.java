package com.gabrielsantana.todolist.user;

public record UserRequestDTO(
        String username,
        String name,
        String password
) {
}
