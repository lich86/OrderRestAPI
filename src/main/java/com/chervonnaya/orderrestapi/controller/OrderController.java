package com.chervonnaya.orderrestapi.controller;

import com.chervonnaya.orderrestapi.dto.OrderDTO;
import com.chervonnaya.orderrestapi.model.Order;
import com.chervonnaya.orderrestapi.model.Views;
import com.chervonnaya.orderrestapi.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
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

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/{id}")
    @JsonView(Views.Order.class)
    public Order getOrder(@PathVariable(name = "id") Long id) {
        return orderService.getById(id);
    }

    @GetMapping
    @JsonView(Views.Order.class)
    public Page<Order> getOrders(Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @PostMapping
    @JsonView(Views.Order.class)
    public Order createOrder(@Valid @RequestBody OrderDTO dto) {
        return orderService.save(dto);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Order.class)
    public Order updateOrder(@PathVariable(name = "id") Long id, @Valid @RequestBody OrderDTO dto) {
        return orderService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Long deleteOrder(@PathVariable(name = "id") Long id) {
        return orderService.delete(id);
    }

}
