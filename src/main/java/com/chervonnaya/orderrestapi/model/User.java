package com.chervonnaya.orderrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(name = "first_name", nullable = false)
    @JsonView(Views.UserSummary.class)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonView(Views.UserSummary.class)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    @JsonView(Views.UserSummary.class)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    @JsonView(Views.UserSummary.class)
    @JsonIgnoreProperties("user")
    private Set<Order> orders;

}
