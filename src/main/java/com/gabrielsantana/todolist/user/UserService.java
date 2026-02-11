package com.gabrielsantana.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.gabrielsantana.todolist.exceptions.UserNotFoundException;
import com.gabrielsantana.todolist.task.TaskService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final TaskService taskService;

    public UserService(UserRepository userRepository, UserMapper mapper, @Lazy TaskService taskService) {
        this.repository = userRepository;
        this.mapper = mapper;
        this.taskService = taskService;
    }

    public List<UserResponseDTO> findAll() {
        List<User> users = repository.findAll();

        return mapper.toUserResponseDTOList(users);
    }

    public UserResponseDTO findByUsername(String username) {
        User user = repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        return mapper.toUserResponseDTO(user);
    }

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO userRequest) {
        User user = mapper.toUserEntity(userRequest);

        String hash = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(hash);
        repository.save(user);

        return mapper.toUserResponseDTO(user);
    }

    public UserResponseDTO delete(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        taskService.deleteTasksByUser(user);
        repository.delete(user);

        return mapper.toUserResponseDTO(user);
    }

    public UserResponseDTO delete(String username) {
        User user = repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        taskService.deleteTasksByUser(user);
        repository.delete(user);

        return mapper.toUserResponseDTO(user);
    }
    public boolean userExists(String username) {
        return repository.findByUsername(username).isPresent();
    }

    public User findEntityByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}
