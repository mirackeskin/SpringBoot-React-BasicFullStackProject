package com.basicproject.api.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int userId;

    @Column(columnDefinition = "text")
    String userName;

    @Column(columnDefinition = "text")
    String email;

    @Column(columnDefinition = "text")
    String password;

    boolean active = false;

    String activationToken ;

    @Column(columnDefinition = "text")
    String image;

    boolean isAdmin = false;
}
