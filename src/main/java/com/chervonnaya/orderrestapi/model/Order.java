package com.chervonnaya.orderrestapi.model;

import com.chervonnaya.orderrestapi.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonView(Views.Order.class)
    @JsonIgnoreProperties("orders")
    private User user;

    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @ElementCollection(fetch = FetchType.EAGER)
    @JsonView(Views.Order.class)
    @Column(name = "product")
    private List<String> products;

    @Column(name="sum")
    @JsonView(Views.Order.class)
    private Integer sum;

    @Column(name = "status")
    @Enumerated
    @JsonView(Views.Order.class)
    private Status status;

}
