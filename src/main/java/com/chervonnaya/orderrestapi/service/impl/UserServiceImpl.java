package com.chervonnaya.orderrestapi.service.impl;

import com.chervonnaya.orderrestapi.dto.UserDTO;
import com.chervonnaya.orderrestapi.exception.EntityNotFoundException;
import com.chervonnaya.orderrestapi.model.User;
import com.chervonnaya.orderrestapi.repository.UserRepository;
import com.chervonnaya.orderrestapi.service.mappers.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl extends CrudServiceImpl<User, UserDTO, UserRepository> {

    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        super(repository, User.class, mapper);
    }


    @Override
    public User getById(Long id) {
        Optional<User> user = repository.findById(id);
        if(user.isPresent()) {
            if(!user.get().getOrders().isEmpty()) {
                log.info(user.get().getOrders().toString());
            } else {
                log.info("nothing");
            }
            log.info(String.format("Found %s with id %s",
                this.genericType.getSimpleName().toLowerCase(), id));
            return user.get();
        }
        log.error(String.format("Unable to get %s with id %s",
            this.genericType.getSimpleName().toLowerCase(), id));
        throw new EntityNotFoundException(this.genericType.getSimpleName(), id);
    }
}
