package com.gabrielsantana.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.gabrielsantana.todolist.exceptions.UserNotFoundException;
import com.gabrielsantana.todolist.task.TaskService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public UserResponseDTO find(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        return mapper.toUserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO userRequest) {
        User user = mapper.toUserEntity(userRequest);

        String hash = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(hash);
        repository.save(user);

        return mapper.toUserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO update(UserUpdateDTO userRequest, UUID userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        mapper.updateEntityFromDTO(userRequest, user);

        if (userRequest.password() != null) {
            String hash = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());

            user.setPassword(hash);
        }

        repository.save(user);

        return mapper.toUserResponseDTO(user);
    }

    public UserResponseDTO delete(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        taskService.deleteTasksByUser(user);
        repository.delete(user);

        return mapper.toUserResponseDTO(user);
    }

    public User findEntityByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}
