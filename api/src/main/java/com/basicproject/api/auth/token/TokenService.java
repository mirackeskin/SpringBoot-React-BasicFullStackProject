package com.basicproject.api.auth.token;

import com.basicproject.api.auth.dto.Credentials;
import com.basicproject.api.user.User;
import com.basicproject.api.user.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
@Service
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
    public User verifyToken(String authorizationHeader) {
        if(authorizationHeader == null) return null;
        var base64encoded = authorizationHeader.split("Basic ")[1];
        var decoded = new String(Base64.getDecoder().decode(base64encoded));
        var credentials = decoded.split(":");
        var email = credentials[0];
        var password = credentials[1];
        User inDb = _userService.findByEmail(email);
        if(inDb == null) return null;
        if(!passwordEncoder.matches(password,inDb.getPassword())) return null;

        return inDb;
    }
}
