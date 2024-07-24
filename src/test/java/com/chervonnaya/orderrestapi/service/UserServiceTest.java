package com.chervonnaya.orderrestapi.service;

import com.chervonnaya.orderrestapi.model.User;
import com.chervonnaya.orderrestapi.repository.UserRepository;
import com.chervonnaya.orderrestapi.service.impl.UserServiceImpl;
import com.chervonnaya.orderrestapi.service.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.chervonnaya.orderrestapi.TestData.user1;
import static com.chervonnaya.orderrestapi.TestData.user2;
import static com.chervonnaya.orderrestapi.TestData.userDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getById_Should_Succeed() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user1));

        assertEquals(user1, userService.getById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getAll_Should_Succeed() {
        Page<User> usersPage = new PageImpl<>(List.of(user1, user2), PageRequest.of(0, 10), 2);
        when(userRepository.findAll(PageRequest.of(0, 10))).thenReturn(usersPage);

        assertEquals(usersPage, userService.findAll(PageRequest.of(0, 10)));
        verify(userRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void save_Should_Succeed() {
        when(mapper.map(userDTO)).thenReturn(user1);
        when(userRepository.save(any())).thenReturn(user1);

        userService.save(userDTO);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void delete_Should_Succeed() {
        user1.setId(1L);
        doNothing().when(userRepository).deleteById(user1.getId());

        userService.delete(user1.getId());

        verify(userRepository, times(1)).deleteById(user1.getId());
    }
}

