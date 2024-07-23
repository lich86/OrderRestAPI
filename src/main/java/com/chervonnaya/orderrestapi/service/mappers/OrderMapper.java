package com.chervonnaya.orderrestapi.service.mappers;

import com.chervonnaya.orderrestapi.dto.OrderDTO;
import com.chervonnaya.orderrestapi.model.Order;
import com.chervonnaya.orderrestapi.model.User;
import com.chervonnaya.orderrestapi.model.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrderMapper extends BaseMapper<Order, OrderDTO> {

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapUser")
    @Mapping(source = "status", target = "status", qualifiedByName = "mapStatus")
    Order map(OrderDTO dto);

    @Named("mapUser")
    static User mapUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Named("mapStatus")
    static Status mapStatus(String status) {
        return Status.valueOf(status);
    }

}
