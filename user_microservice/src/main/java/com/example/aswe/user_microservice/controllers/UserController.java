package com.example.aswe.user_microservice.controllers;

import com.example.aswe.user_microservice.models.User;
import com.example.aswe.user_microservice.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> creds, HttpSession session) {
        User user = userRepository.findByUsername(creds.get("username"));
        if (user != null && BCrypt.checkpw(creds.get("password"), user.getPassword())) {
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/Logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return new ResponseEntity<>("Access denied: ADMIN only", HttpStatus.FORBIDDEN);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>("User deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable int id) {
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/Registeration")
    public ResponseEntity<User> createOrUpdate(@RequestBody User user) {
        // Encrypt password if it's new or being updated
        if (user.getPassword() != null) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
        }
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchByName(@RequestParam String name) {
        return new ResponseEntity<>(userRepository.findByNameContainingIgnoreCase(name), HttpStatus.OK);
    }

    @PostMapping("/edit-role/{id}")
    public ResponseEntity<Void> updateRole(@PathVariable int id, @RequestBody Map<String, String> payload) {
        String role = payload.get("role");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        user.setRole(role);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
