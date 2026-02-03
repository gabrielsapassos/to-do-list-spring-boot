package com.gabrielsantana.todolist.task;

import com.gabrielsantana.todolist.exceptions.TaskNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task create(Task request) {

        return taskRepository.save(request);
    }
}
