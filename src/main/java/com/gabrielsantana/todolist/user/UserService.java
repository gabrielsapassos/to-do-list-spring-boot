package com.gabrielsantana.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.gabrielsantana.todolist.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.repository = userRepository;
        this.mapper = mapper;
    }

    public List<UserResponseDTO> findAll() {
        List<User> users = repository.findAll();

        return mapper.toUserResponseDTOList(users);
    }

    public UserResponseDTO findByUsername(String username) {
        User user = repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

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
}
