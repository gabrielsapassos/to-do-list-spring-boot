package com.gabrielsantana.todolist.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must contain at least 6 digits")
        String password
) {
}
