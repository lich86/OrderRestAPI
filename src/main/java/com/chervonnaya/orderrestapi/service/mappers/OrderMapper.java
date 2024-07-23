package com.chervonnaya.orderrestapi.service.mappers;

import com.chervonnaya.orderrestapi.dto.OrderDTO;
import com.chervonnaya.orderrestapi.model.Order;
import com.chervonnaya.orderrestapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrderMapper extends BaseMapper<Order, OrderDTO> {

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapUser")
    Order map(OrderDTO dto);

    @Named("mapUser")
    static User mapUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

}
