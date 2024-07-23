package com.chervonnaya.orderrestapi.service.mappers;

import com.chervonnaya.orderrestapi.dto.BaseDTO;
import com.chervonnaya.orderrestapi.model.BaseEntity;

public interface BaseMapper<E extends BaseEntity, D extends BaseDTO> {
    E map(D dto);
}
