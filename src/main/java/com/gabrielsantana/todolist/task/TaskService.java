package com.gabrielsantana.todolist.task;

import com.gabrielsantana.todolist.exceptions.TaskNotFoundException;
import com.gabrielsantana.todolist.exceptions.UserNotFoundException;
import com.gabrielsantana.todolist.user.User;
import com.gabrielsantana.todolist.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, TaskMapper mapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Transactional
    public TaskResponseDTO create(TaskRequestDTO request) {
        User user = userRepository.findById(request.userId())
                                  .orElseThrow(() -> new UserNotFoundException(request.userId()));

        Task task = mapper.toEntity(request, user);
        taskRepository.save(task);

        return mapper.toResponseDTO(task);
    }
}
