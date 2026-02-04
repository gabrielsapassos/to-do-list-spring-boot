package com.gabrielsantana.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.gabrielsantana.todolist.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.repository = userRepository;
        this.mapper = mapper;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO userRequest) {
        User user = mapper.toUserEntity(userRequest);

        String hash = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(hash);
        repository.save(user);

        return mapper.toUserResponseDTO(user);
    }
}
