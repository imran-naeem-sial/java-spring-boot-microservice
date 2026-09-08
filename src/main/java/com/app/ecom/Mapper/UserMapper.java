package com.app.ecom.Mapper;

import com.app.ecom.DTOs.UserDto;
import com.app.ecom.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    void updateProductFromDto(UserDto userDto, @MappingTarget User existingUser);
}
