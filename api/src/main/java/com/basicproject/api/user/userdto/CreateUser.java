package com.basicproject.api.user.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUser {
    @NotBlank
    String userName;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String password;
}
