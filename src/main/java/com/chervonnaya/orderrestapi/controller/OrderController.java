package com.chervonnaya.orderrestapi.controller;

import com.chervonnaya.orderrestapi.dto.OrderDTO;
import com.chervonnaya.orderrestapi.model.Order;
import com.chervonnaya.orderrestapi.model.Views;
import com.chervonnaya.orderrestapi.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.validation.Valid;
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
@RequestMapping("/api/order")
public class OrderController {
    private final OrderServiceImpl orderService;
    private final ObjectMapper mapper;

    @Autowired
    public OrderController(OrderServiceImpl orderService, ObjectMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @GetMapping(value = "/{id}")
    public String getOrder(@PathVariable(name = "id") Long id) throws JsonProcessingException {
        Order order = orderService.getById(id);
        return mapper.writerWithView(Views.Order.class).writeValueAsString(order);
    }

    @GetMapping
    public String getOrders(Pageable pageable) throws JsonProcessingException {
        Page<Order> page = orderService.findAll(pageable);
        return mapper.writerWithView(Views.Order.class).writeValueAsString(page);
    }

    @PostMapping
    public String createOrder(@Valid @RequestBody OrderDTO dto) throws JsonProcessingException {
        Order order = orderService.save(dto);
        return mapper.writerWithView(Views.Order.class).writeValueAsString(order);
    }

    @PutMapping("/{id}")
    public String updateOrder(@PathVariable(name = "id") Long id, @Valid @RequestBody OrderDTO dto) throws JsonProcessingException {
        Order order = orderService.update(id, dto);
        return mapper.writerWithView(Views.Order.class).writeValueAsString(order);
    }

    @DeleteMapping("/{id}")
    public Long deleteOrder(@PathVariable(name = "id") Long id) {
        return orderService.delete(id);
    }

}
