package com.chervonnaya.orderrestapi.controller;

import com.chervonnaya.orderrestapi.dto.UserDTO;
import com.chervonnaya.orderrestapi.model.User;
import com.chervonnaya.orderrestapi.model.Views;
import com.chervonnaya.orderrestapi.service.impl.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public MappingJacksonValue getUser(@RequestParam(value = "view", defaultValue = "summary") String view,
                                       @PathVariable(name = "id") Long id) {
        User user = userService.getById(id);
        Class<?> viewClass = switch (view) {
            case "summary" -> Views.UserSummary.class;
            case "detail" -> Views.UserDetails.class;
            default -> Views.UserDetails.class;
        };
        MappingJacksonValue value = new MappingJacksonValue(user);
        value.setSerializationView(viewClass);
        return value;
    }


    @GetMapping
    @JsonView(Views.UserSummary.class)
    public Page<User> getUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @PostMapping
    @JsonView(Views.UserSummary.class)
    public User createUser(@Valid @RequestBody UserDTO dto) {
        return userService.save(dto);
    }

    @PutMapping("/{id}")
    @JsonView(Views.UserSummary.class)
    public User updateUser(@PathVariable(name = "id") Long id,
                           @Valid @RequestBody UserDTO dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Long deleteUser(@PathVariable(name = "id") Long id) {
        return userService.delete(id);
    }
}
