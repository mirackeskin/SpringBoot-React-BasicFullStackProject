package com.basicproject.api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Credentials {
    @NotBlank
    @Email
    String email;
    @NotBlank
    String password;
}
