package com.chervonnaya.orderrestapi.controller;

import com.chervonnaya.orderrestapi.model.Order;
import com.chervonnaya.orderrestapi.service.impl.OrderServiceImpl;
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

import static com.chervonnaya.orderrestapi.TestData.emptyOrder;
import static com.chervonnaya.orderrestapi.TestData.invalidStatusJson;
import static com.chervonnaya.orderrestapi.TestData.order1;
import static com.chervonnaya.orderrestapi.TestData.order2;
import static com.chervonnaya.orderrestapi.TestData.orderDTO;
import static com.chervonnaya.orderrestapi.TestData.orderURL;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl serviceMock;


    @Test
    void getOrder_Should_Succeed() throws Exception {
        when(serviceMock.getById(any())).thenReturn(order1);

        mockMvc.perform(get(orderURL + "/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user.firstName").value("Name"))
            .andExpect(jsonPath("$.user.lastName").value("LastName"))
            .andExpect(jsonPath("$.user.email").value("email@email.com"))
            .andExpect(jsonPath("$.user.password").doesNotExist())
            .andExpect(jsonPath("$.products", containsInAnyOrder("Product1", "Product2", "Product3")))
            .andExpect(jsonPath("$.sum").value("2000"))
            .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void getAllOrders_Should_Succeed() throws Exception {
        Page<Order> ordersPage = new PageImpl<>(List.of(order1, order2), PageRequest.of(0, 10), 2);

        when(serviceMock.findAll(PageRequest.of(0, 10))).thenReturn(ordersPage);

        mockMvc.perform(get(orderURL)
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void postOrder_Should_Succeed() throws Exception {
        when(serviceMock.save(any())).thenReturn(order1);

        mockMvc.perform(post(orderURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderDTO)))
            .andExpect(status().isOk());
    }

    @Test
    void postOrderEmptyJson_Should_Fail() throws Exception {
        mockMvc.perform(post(orderURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emptyOrder)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Please provide at least one product")))
            .andExpect(jsonPath("$.message", containsString("Please provide correct user_id")))
            .andExpect(jsonPath("$.message", containsString("Please provide correct sum")))
            .andExpect(jsonPath("$.message", containsString("Please provide correct status")));
    }

    @Test
    void postOrderInvalidJson_Should_Fail() throws Exception {
        mockMvc.perform(post(orderURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidStatusJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is("Invalid JSON input: Please provide correct status value")));
    }

    @Test
    void putOrder_Should_Succeed() throws Exception {
        when(serviceMock.save(any())).thenReturn(order1);

        mockMvc.perform(put(orderURL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderDTO)))
            .andExpect(status().isOk());
    }

    @Test
    void putOrder_Should_Fail() throws Exception {
        mockMvc.perform(put(orderURL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emptyOrder)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Please provide at least one product")))
            .andExpect(jsonPath("$.message", containsString("Please provide correct user_id")))
            .andExpect(jsonPath("$.message", containsString("Please provide correct sum")))
            .andExpect(jsonPath("$.message", containsString("Please provide correct status")));
    }

    @Test
    void deleteOrder_Should_Succeed() throws Exception {
        when(serviceMock.delete(any())).thenReturn(1L);

        mockMvc.perform(delete(orderURL + "/1"))
            .andExpect(status().isOk());
    }
}
