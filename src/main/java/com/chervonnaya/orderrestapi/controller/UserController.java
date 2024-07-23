package com.chervonnaya.orderrestapi.controller;

import com.chervonnaya.orderrestapi.dto.UserDTO;
import com.chervonnaya.orderrestapi.model.User;
import com.chervonnaya.orderrestapi.model.Views;
import com.chervonnaya.orderrestapi.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl userService;
    private final ObjectMapper mapper;

    @Autowired
    public UserController(UserServiceImpl userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable(name = "id") Long id) throws JsonProcessingException {
        return userService.getById(id);
    }

    @GetMapping
    public String getUsers(Pageable pageable) throws JsonProcessingException {
        Page<User> page = userService.findAll(pageable);
        return mapper.writerWithView(Views.UserSummary.class).writeValueAsString(page);
    }

    @PostMapping
    public String createUser(@RequestBody UserDTO dto) throws JsonProcessingException {
        User user = userService.save(dto);
        return mapper.writerWithView(Views.UserSummary.class).writeValueAsString(user);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable(name = "id") Long id, @RequestBody UserDTO dto) throws JsonProcessingException {
        User user = userService.update(id, dto);
        return mapper.writerWithView(Views.UserSummary.class).writeValueAsString(user);
    }

    @DeleteMapping("/{id}")
    public Long deleteUser(@PathVariable(name = "id") Long id) {
        return userService.delete(id);
    }
}
