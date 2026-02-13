package com.gabrielsantana.todolist.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO (
        @Size(min = 2, message = "Name must contain at least 2 digits")
        @Pattern(
                regexp = "^(?!\\s*$).+", // Prevents only spaces
                message = "Name must contain digits"
        )
        String name,

        @Size(min = 3, message = "Username must contain at least 3 digits")
        @Pattern(
                regexp = "^(?!\\s*$).+", // Prevents only spaces
                message = "Username must contain digits"
        )
        String username,

        @Size(min = 6, message = "Password must contain at least 6 digits")
        @Pattern(
                regexp = "^(?!\\s*$).+", // Prevents only spaces
                message = "Password must contain digits"
        )
        String password
) {}
