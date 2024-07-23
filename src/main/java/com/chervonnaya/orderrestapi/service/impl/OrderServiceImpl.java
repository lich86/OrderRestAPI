package com.chervonnaya.orderrestapi.service.impl;

import com.chervonnaya.orderrestapi.dto.OrderDTO;
import com.chervonnaya.orderrestapi.model.Order;
import com.chervonnaya.orderrestapi.repository.OrderRepository;
import com.chervonnaya.orderrestapi.service.mappers.OrderMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends CrudServiceImpl<Order, OrderDTO, OrderRepository> {

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
        super(repository, Order.class, mapper);
    }


}
