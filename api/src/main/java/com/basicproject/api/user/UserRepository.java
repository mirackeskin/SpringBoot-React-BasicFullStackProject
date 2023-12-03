package com.basicproject.api.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByActivationToken(String activationToken);
    User findByEmail(String email);

}
