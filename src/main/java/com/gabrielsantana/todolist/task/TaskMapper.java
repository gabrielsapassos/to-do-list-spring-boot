package com.gabrielsantana.todolist.task;

import com.gabrielsantana.todolist.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TaskMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    Task toEntity(TaskRequestDTO requestDTO, User user);

    @Mapping(target = "userId", source = "task.user.id")
    TaskResponseDTO toResponseDTO(Task task);

    List<TaskResponseDTO> toResponseDTOList(List<Task> tasks);
}
