package com.example.aswe.user_microservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.user_microservice.models.User;

// package com.example.userservice.usermicroservice;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    List<User> findByNameContainingIgnoreCase(String name);

}
