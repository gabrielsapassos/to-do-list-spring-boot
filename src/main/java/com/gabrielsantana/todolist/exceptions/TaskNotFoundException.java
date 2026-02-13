package com.gabrielsantana.todolist.exceptions;

import com.gabrielsantana.todolist.user.User;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Couldn't find task with id: " + id);
    }
    public TaskNotFoundException(User user) {
        super("Couldn't find task with user: " + user.getUsername());
    }
    public TaskNotFoundException(UUID userId) {
        super("Couldn't find any task with user id: " + userId);
    }
    public TaskNotFoundException(Long id, UUID userId) {
        super("Couldn't find any task with id: " + id + " or it doesn't belong to the user: " + userId);
    }

}
