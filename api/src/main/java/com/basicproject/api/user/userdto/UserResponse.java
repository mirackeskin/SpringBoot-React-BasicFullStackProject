package com.basicproject.api.user.userdto;

import com.basicproject.api.user.User;
import lombok.Data;

@Data
public class UserResponse {
    int userId;
    String userName;
    String email;
    String image;

    public UserResponse(User user){
        this.userId=user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.image = user.getImage();
    }
}
