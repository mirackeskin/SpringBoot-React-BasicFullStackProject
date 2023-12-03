package com.basicproject.api.auth.token;

import com.basicproject.api.auth.dto.Credentials;
import com.basicproject.api.user.User;

public interface ITokenService {
    public Token createToken(Credentials credentials);
    public User verifyToken(String AuthorizationHeader);
}
