package com.basicproject.api.auth.dto;

import com.basicproject.api.auth.token.Token;
import com.basicproject.api.user.User;
import lombok.Data;

@Data
public class AuthResponse {
    User user;
    Token token;
}
