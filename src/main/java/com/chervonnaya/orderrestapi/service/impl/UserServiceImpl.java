package com.chervonnaya.orderrestapi.service.impl;

import com.chervonnaya.orderrestapi.dto.UserDTO;
import com.chervonnaya.orderrestapi.model.User;
import com.chervonnaya.orderrestapi.repository.UserRepository;
import com.chervonnaya.orderrestapi.service.mappers.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends CrudServiceImpl<User, UserDTO, UserRepository> {

    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        super(repository, User.class, mapper);
    }

}
