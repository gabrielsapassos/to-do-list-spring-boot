package com.gabrielsantana.todolist.user;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    User toUserEntity(UserRequestDTO userRequestDTO);
    User toUserEntity(UserResponseDTO userResponseDTO);

    List<UserResponseDTO> toUserResponseDTOList (List<User> users);
    UserResponseDTO toUserResponseDTO(User user);

    // @MappingTarget ensures that the Mapper won't create a new User entity, but instead updates the existing one
    void updateEntityFromDTO(UserUpdateDTO userUpdateDTO, @MappingTarget User entity);

    // Condition to ignore null or blank strings during mapping
    @Condition
    default boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}
