package com.basicproject.api.auth;

import com.basicproject.api.auth.dto.AuthResponse;
import com.basicproject.api.auth.dto.Credentials;
import com.basicproject.api.auth.exception.AuthenticationException;
import com.basicproject.api.auth.token.ITokenService;
import com.basicproject.api.auth.token.Token;
import com.basicproject.api.auth.token.TokenService;
import com.basicproject.api.user.User;
import com.basicproject.api.user.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthService {

    UserService _userService;
    ITokenService _tokenService;

    public AuthService(UserService userService, TokenService tokenService){
        _userService = userService;
        _tokenService = tokenService;
    }
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public AuthResponse authenticateUser(Credentials credentials){
        User inDb = _userService.findByEmail(credentials.getEmail());
        if(inDb == null){
            throw new AuthenticationException("Kullanıcı Bulunamadı");
        }
        if(!passwordEncoder.matches(inDb.getPassword(), credentials.getPassword())){
            throw new AuthenticationException("Kullanıcı Şifresi Eşleşmedi");
        }
        Token token = _tokenService.createToken(credentials);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUser(inDb);
        return authResponse;
    }

}
