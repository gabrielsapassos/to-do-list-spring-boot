package com.gabrielsantana.todolist.task;

import com.gabrielsantana.todolist.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    public ResponseEntity<List<TaskResponseDTO>> getTasks(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("user");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<TaskResponseDTO> tasks = taskService.findByUserId(userId);

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO response = taskService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<TaskResponseDTO> crateTask(@Valid @RequestBody TaskRequestDTO request) {
        TaskResponseDTO task = taskService.create(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.userId())
                .toUri();

        return ResponseEntity.created(location).body(task);
    }
}
