package com.gabrielsantana.todolist.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String username;
    private String name;
    private String password;
}
