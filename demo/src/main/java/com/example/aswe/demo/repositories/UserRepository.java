package com.example.aswe.demo.repositories;

import com.example.aswe.demo.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    // User findByUsername(String username);
    List<User> findByNameContainingIgnoreCase(String name);
}