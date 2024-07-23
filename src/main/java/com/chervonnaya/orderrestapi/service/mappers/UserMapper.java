package com.chervonnaya.orderrestapi.service.mappers;

import com.chervonnaya.orderrestapi.dto.UserDTO;
import com.chervonnaya.orderrestapi.model.User;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;


@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper extends BaseMapper<User, UserDTO> {

    @Mapping(target = "id", ignore = true)
    User map(UserDTO dto);
}
