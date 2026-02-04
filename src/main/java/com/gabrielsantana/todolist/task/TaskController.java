package com.gabrielsantana.todolist.task;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task response = taskService.findById(id);
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
