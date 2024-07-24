package com.chervonnaya.orderrestapi.service;

import com.chervonnaya.orderrestapi.model.Order;
import com.chervonnaya.orderrestapi.repository.OrderRepository;
import com.chervonnaya.orderrestapi.service.impl.OrderServiceImpl;
import com.chervonnaya.orderrestapi.service.mappers.OrderMapper;
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

import static com.chervonnaya.orderrestapi.TestData.order1;
import static com.chervonnaya.orderrestapi.TestData.order2;
import static com.chervonnaya.orderrestapi.TestData.orderDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper mapper;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void getById_Should_Succeed() {
        when(orderRepository.findById(any())).thenReturn(Optional.of(order1));

        assertEquals(order1, orderService.getById(1L));
        verify(orderRepository, times(1)).findById(1L);

    }


    @Test
    void getAll_Should_Succeed() {
        Page<Order> ordersPage = new PageImpl<>(List.of(order1, order2), PageRequest.of(0, 10), 2);
        when(orderRepository.findAll(PageRequest.of(0, 10))).thenReturn(ordersPage);

        assertEquals(ordersPage, orderService.findAll(PageRequest.of(0, 10)));
        verify(orderRepository, times(1)).findAll(PageRequest.of(0, 10));
    }


    @Test
    void save_Should_Succeed() {
        when(mapper.map(orderDTO)).thenReturn(order1);
        when(orderRepository.save(any())).thenReturn(order1);

        orderService.save(orderDTO);

        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void delete_Should_Succeed() {
        order1.setId(1L);
        doNothing().when(orderRepository).deleteById(order1.getId());

        orderService.delete(order1.getId());

        verify(orderRepository, times(1)).deleteById(order1.getId());
    }
}
