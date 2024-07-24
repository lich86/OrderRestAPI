package com.chervonnaya.orderrestapi.controller;

import com.chervonnaya.orderrestapi.model.User;
import com.chervonnaya.orderrestapi.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.chervonnaya.orderrestapi.TestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl serviceMock;

    @Test
    void getUserDefault_Should_Succeed() throws Exception {
        when(serviceMock.getById(any())).thenReturn(user1);

        mockMvc.perform(get(userURL + "/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Name"))
            .andExpect(jsonPath("$.lastName").value("LastName"))
            .andExpect(jsonPath("$.email").value("email@email.com"))
            .andExpect(jsonPath("$.password").doesNotExist())
            .andExpect(jsonPath("$.orders").doesNotExist());
    }

    @Test
    void getUserSummary_Should_Succeed() throws Exception {
        when(serviceMock.getById(any())).thenReturn(user1);

        mockMvc.perform(get(userURL + "/1?view=summary"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Name"))
            .andExpect(jsonPath("$.lastName").value("LastName"))
            .andExpect(jsonPath("$.email").value("email@email.com"))
            .andExpect(jsonPath("$.password").doesNotExist())
            .andExpect(jsonPath("$.orders").doesNotExist());
    }

    @Test
    void getUserDetailsView_Should_Succeed() throws Exception {
        when(serviceMock.getById(any())).thenReturn(user1);

        mockMvc.perform(get(userURL + "/1?view=detail"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Name"))
            .andExpect(jsonPath("$.lastName").value("LastName"))
            .andExpect(jsonPath("$.email").value("email@email.com"))
            .andExpect(jsonPath("$.password").doesNotExist())
            .andExpect(jsonPath("$.orders").exists());
    }

    @Test
    void getAllUsers_Should_Succeed() throws Exception {
        Page<User> usersPage = new PageImpl<>(List.of(user1, user2), PageRequest.of(0, 10), 2);

        when(serviceMock.findAll(PageRequest.of(0, 10))).thenReturn(usersPage);

        mockMvc.perform(get(userURL)
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void postUser_Should_Succeed() throws Exception {
        when(serviceMock.save(any())).thenReturn(user1);

        mockMvc.perform(post(userURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDTO)))
            .andExpect(status().isOk());
    }

    @Test
    void postUser_Should_Fail() throws Exception {
        mockMvc.perform(post(userURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emptyUser)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Please provide the first name")))
            .andExpect(jsonPath("$.message", containsString("Please provide the last name")))
            .andExpect(jsonPath("$.message", containsString("Please provide a password")))
            .andExpect(jsonPath("$.message", containsString("Please provide a correct email")));
    }

    @Test
    void putUser_Should_Succeed() throws Exception {
        when(serviceMock.save(any())).thenReturn(user1);

        mockMvc.perform(put(userURL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDTO)))
            .andExpect(status().isOk());
    }

    @Test
    void putUser_Should_Fail() throws Exception {
        mockMvc.perform(put(userURL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emptyUser)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Please provide the first name")))
            .andExpect(jsonPath("$.message", containsString("Please provide the last name")))
            .andExpect(jsonPath("$.message", containsString("Please provide a password")))
            .andExpect(jsonPath("$.message", containsString("Please provide a correct email")));
    }

    @Test
    void deleteUser_Should_Succeed() throws Exception {
        when(serviceMock.delete(any())).thenReturn(1L);

        mockMvc.perform(delete(userURL + "/1"))
            .andExpect(status().isOk());
    }
}
