package com.chervonnaya.orderrestapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BaseDTO {
    @NotBlank(message = "{firstName.notBlank}")
    private String firstName;
    @NotBlank(message = "{lastName.notBlank}")
    private String lastName;
    @Email(message = "{email.Message}")
    @NotBlank(message = "{email.Message}")
    private String email;
    @NotBlank(message = "{password.notBlank}")
    private String password;
}
