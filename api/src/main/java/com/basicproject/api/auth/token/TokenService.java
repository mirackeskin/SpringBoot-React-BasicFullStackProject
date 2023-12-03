package com.basicproject.api.auth.token;

import com.basicproject.api.auth.dto.Credentials;
import com.basicproject.api.user.User;
import com.basicproject.api.user.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;

public class TokenService implements ITokenService {

    UserService _userService;
    public TokenService(UserService userService){
        _userService = userService;
    }
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public Token createToken(Credentials credentials) {
        String pureString = credentials.getEmail()+":"+credentials.getPassword();
        String encodedString = Base64.getEncoder().encodeToString(pureString.getBytes());
        Token token = new Token();
        token.setToken(encodedString);
        token.setPrefix("Basic");
        return token;
    }

    @Override
    public User verifyToken(String AuthorizationHeader) {
        return null;
    }
}
