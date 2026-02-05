package com.gabrielsantana.todolist.user;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    User toUserEntity(UserRequestDTO userRequestDTO);

    List<UserResponseDTO> toUserResponseDTOList (List<User> users);
    UserResponseDTO toUserResponseDTO(User user);
}
