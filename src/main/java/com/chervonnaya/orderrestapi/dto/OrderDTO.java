package com.chervonnaya.orderrestapi.dto;

import com.chervonnaya.orderrestapi.model.enums.Status;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends BaseDTO {
    @NotNull(message = "{userId.notNull}")
    private Long userId;
    @NotEmpty(message = "{products.notEmpty}")
    private List<String> products;
    @NotNull(message = "{sum.notNull}")
    @DecimalMin(value = "0.0", message = "{sum.Min}")
    @DecimalMax(value = "9999999999999.99", message = "{sum.Max}")
    private BigDecimal sum;
    @NotNull
    private Status status;
}
