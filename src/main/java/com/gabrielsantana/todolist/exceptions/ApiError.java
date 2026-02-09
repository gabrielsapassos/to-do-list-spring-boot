package com.gabrielsantana.todolist.exceptions;

import java.time.LocalDateTime;

public record ApiError (
    int status,
    String message,
    LocalDateTime timestamp
) {}
