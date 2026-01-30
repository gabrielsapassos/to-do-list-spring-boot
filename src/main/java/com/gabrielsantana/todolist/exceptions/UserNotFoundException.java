package com.gabrielsantana.todolist.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("Couldn't find user with specified username: " + username);
    }
    public UserNotFoundException(UUID uuid) {
        super("Couldn't find user with specified UUID: " + uuid);
    }

}
