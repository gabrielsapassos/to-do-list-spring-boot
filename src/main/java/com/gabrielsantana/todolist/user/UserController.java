package com.gabrielsantana.todolist.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> findUser(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        UserResponseDTO user = service.find(userId);

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

    @DeleteMapping("/me")
    public ResponseEntity<UserResponseDTO> deleteUser(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");

        return ResponseEntity.ok(service.delete(userId));
    }
}
