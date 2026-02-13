package com.gabrielsantana.todolist.task;

import com.gabrielsantana.todolist.user.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    Task toEntity(TaskRequestDTO requestDTO, User user);

    @Mapping(target = "userId", source = "task.user.id")
    TaskResponseDTO toResponseDTO(Task task);

    List<TaskResponseDTO> toResponseDTOList(List<Task> tasks);

    void updateFromTaskPutDTO(TaskPutDTO taskPutDTO, @MappingTarget Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromTaskPatchDTO(TaskPatchDTO taskPatchDTO, @MappingTarget Task task);
}
