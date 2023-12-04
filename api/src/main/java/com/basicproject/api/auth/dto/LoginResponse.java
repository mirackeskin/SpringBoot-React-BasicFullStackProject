package com.basicproject.api.auth.dto;

import com.basicproject.api.user.User;
import lombok.Data;

@Data
public class LoginResponse {

    int userId;
    String userName;
    String email;
    String image;

    public LoginResponse (User user){
        setUserId(user.getUserId());
        setUserName(user.getUserName());
        setEmail(user.getEmail());
        setImage(user.getImage());
    }

}
