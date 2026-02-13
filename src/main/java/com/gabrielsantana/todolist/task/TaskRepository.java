package com.gabrielsantana.todolist.task;

import com.gabrielsantana.todolist.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findByUser(User user);

    Optional<List<Task>> findByUserId(UUID user);

    Optional<Task> findEntityByIdAndUserId(Long id, UUID userId);
}
