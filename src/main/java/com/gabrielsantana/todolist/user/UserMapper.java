package com.gabrielsantana.todolist.user;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    User toUserEntity(UserRequestDTO userRequestDTO);

    UserResponseDTO toUserResponseDTO(User user);
}
