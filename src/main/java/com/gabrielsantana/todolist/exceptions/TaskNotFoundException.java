package com.gabrielsantana.todolist.exceptions;

import com.gabrielsantana.todolist.user.User;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Couldn't find task with id: " + id);
    }
    public TaskNotFoundException(User user) {
        super("Couldn't find task with user: " + user.getUsername());
    }

}
