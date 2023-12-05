package com.basicproject.api.user;

import com.basicproject.api.user.userdto.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByActivationToken(String activationToken);
    User findByEmail(String email);

    User findById(int id);



}
