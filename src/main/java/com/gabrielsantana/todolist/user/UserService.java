package com.gabrielsantana.todolist.user;

import com.gabrielsantana.todolist.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public User create(User user) {
        return repository.save(user);
    }
}
