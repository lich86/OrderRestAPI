package com.chervonnaya.orderrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends BaseDTO {
    private Long userId;
    private List<String> products;
    private int sum;
    private String status;
}
