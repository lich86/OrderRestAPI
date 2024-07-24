package com.chervonnaya.orderrestapi;

import com.chervonnaya.orderrestapi.dto.OrderDTO;
import com.chervonnaya.orderrestapi.dto.UserDTO;
import com.chervonnaya.orderrestapi.model.Order;
import com.chervonnaya.orderrestapi.model.User;
import com.chervonnaya.orderrestapi.model.enums.Status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData {
    public static final User user1 = new User();
    public static final User user2 = new User();
    public static final Order order1 = new Order();
    public static final Order order2 = new Order();
    public static final UserDTO userDTO = new UserDTO();
    public static final OrderDTO orderDTO = new OrderDTO();
    public static final UserDTO emptyUser = new UserDTO();
    public static final OrderDTO emptyOrder = new OrderDTO();
    public static final String userURL = "/api/user";
    public static final String orderURL = "/api/order";
    public static final String invalidStatusJson = "{\"userId\": 1, \"products\": [\"Fan\"], \"sum\": 300, \"status\": \"IN\"}";

    static {
        user1.setFirstName("Name");
        user1.setLastName("LastName");
        user1.setEmail("email@email.com");
        user1.setPassword("password");

        user2.setFirstName("Name2");
        user2.setLastName("LastName2");
        user2.setEmail("email2@email.com");
        user2.setPassword("password");

        order1.setUser(user1);
        order1.setProducts(Arrays.asList("Product1", "Product2", "Product3"));
        order1.setSum(new BigDecimal("2000"));
        order1.setStatus(Status.IN_PROGRESS);

        order2.setUser(user2);
        order2.setProducts(Arrays.asList("Product1", "Product2", "Product3"));
        order2.setSum(new BigDecimal("2000"));
        order2.setStatus(Status.PAID);


        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        user1.setOrders(orders);

        userDTO.setFirstName("Name");
        userDTO.setLastName("LastName");
        userDTO.setEmail("email@email.com");
        userDTO.setPassword("password");

        orderDTO.setUserId(1L);
        orderDTO.setProducts(Arrays.asList("Product1", "Product2", "Product3"));
        orderDTO.setSum(new BigDecimal("2000"));
        orderDTO.setStatus(Status.IN_PROGRESS);

    }
}
