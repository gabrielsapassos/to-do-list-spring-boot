package com.gabrielsantana.todolist.task;

import com.gabrielsantana.todolist.exceptions.TaskNotFoundException;
import com.gabrielsantana.todolist.user.User;
import com.gabrielsantana.todolist.user.UserMapper;
import com.gabrielsantana.todolist.user.UserResponseDTO;
import com.gabrielsantana.todolist.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper mapper, UserService userService, UserMapper userMapper) {
        this.repository = taskRepository;
        this.mapper = mapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public TaskResponseDTO findById(Long taskId, UUID userId) {
        Task task = repository.findEntityByIdAndUserId(taskId, userId)
                              .orElseThrow((() -> new TaskNotFoundException(taskId, userId)));

        return mapper.toResponseDTO(task);
    }

    public List<TaskResponseDTO> findByUserId(UUID userId) {
        List<Task> tasks = repository.findByUserId(userId).orElseThrow(() -> new TaskNotFoundException(userId));

        return mapper.toResponseDTOList(tasks);
    }

    @Transactional
    public TaskResponseDTO create(TaskRequestDTO request, UUID userId) {
        UserResponseDTO userResponseDTO = userService.find(userId);
        User user = userMapper.toUserEntity(userResponseDTO);

        Task task = mapper.toEntity(request, user);
        repository.save(task);

        return mapper.toResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO update(TaskUpdateDTO request, UUID userId, Long taskId) {
        Task task = repository.findEntityByIdAndUserId(taskId, userId)
                              .orElseThrow(() -> new TaskNotFoundException(taskId, userId));
        mapper.updateFromTaskUpdateDTO(request, task);
        repository.save(task);

        return mapper.toResponseDTO(task);
    }

    public void deleteById(Long id) {
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        repository.delete(task);
    }

    public void deleteTasksByUser(User user) {
        List<Task> tasks = repository.findByUser(user).orElseThrow(() -> new TaskNotFoundException(user));

        repository.deleteAll(tasks);
    }
}
