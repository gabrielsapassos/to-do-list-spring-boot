package com.gabrielsantana.todolist.task;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<TaskResponseDTO>> findTasks(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");

        List<TaskResponseDTO> tasks = taskService.findByUserId(userId);

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> findTaskById(@PathVariable Long id, HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");

        TaskResponseDTO response = taskService.findById(id, userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO, HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");

        TaskResponseDTO task = taskService.create(taskRequestDTO, userId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.userId())
                .toUri();

        return ResponseEntity.created(location).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@Valid @RequestBody TaskUpdateDTO updateRequest,
                                                      @PathVariable Long id,
                                                      HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");

        TaskResponseDTO task = taskService.update(updateRequest, userId, id);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);

        return ResponseEntity.notFound().build();
    }
}
