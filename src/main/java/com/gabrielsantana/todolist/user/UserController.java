package com.gabrielsantana.todolist.user;

import jakarta.validation.constraints.NotBlank;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = service.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        UserResponseDTO user = service.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequest) {
        UserResponseDTO user = service.create(userRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(location).body(user);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> deleteUserById(@PathVariable UUID id) throws BadRequestException {
        UserResponseDTO responseDTO = service.delete(id);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> deleteUserByUsername(@PathVariable @NotBlank String username) throws BadRequestException {
        UserResponseDTO responseDTO = service.delete(username);

        return ResponseEntity.ok(responseDTO);
    }
}
