package com.gabrielsantana.todolist.task;

import com.gabrielsantana.todolist.exceptions.TaskNotFoundException;
import com.gabrielsantana.todolist.user.User;
import com.gabrielsantana.todolist.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, TaskMapper mapper, UserService userService) {
        this.repository = taskRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    public List<TaskResponseDTO> findALl() {
        List<Task> tasks = repository.findAll();

        return mapper.toResponseDTOList(tasks);
    }

    public TaskResponseDTO findById(Long id) {
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        return mapper.toResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO create(TaskRequestDTO request) {
        User user = userService.findById(request.userId());

        Task task = mapper.toEntity(request, user);
        repository.save(task);

        return mapper.toResponseDTO(task);
    }

    public void deleteTasksByUser(User user) {
        List<Task> tasks = repository.findByUser(user).orElseThrow(() -> new TaskNotFoundException(user));

        repository.deleteAll(tasks);
    }
}
