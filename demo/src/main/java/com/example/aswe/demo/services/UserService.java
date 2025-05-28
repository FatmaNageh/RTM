package com.example.aswe.demo.services;

import com.example.aswe.demo.models.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8081/User";

    public UserService() {
        this.restTemplate = new RestTemplate();
    }

    public List<User> findAll() {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                });
        return response.getBody();
    }

    public void save(User user) {
        restTemplate.postForObject(baseUrl + "/Registeration", user, User.class);
    }

    public User findById(int id) {
        return restTemplate.getForObject(baseUrl + "/" + id, User.class);
    }

    public void deleteById(int id) {
        restTemplate.delete(baseUrl + "/" + id);
    }

    public User login(String username, String password) {
        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);

        try {
            return restTemplate.postForObject(baseUrl + "/Login", payload, User.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> searchByName(String name) {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                baseUrl + "/search?name=" + name,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                });
        return response.getBody();
    }

    public void updateRole(int id, String role) {
        Map<String, String> payload = new HashMap<>();
        payload.put("role", role);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload);
        restTemplate.postForEntity(baseUrl + "/edit-role/" + id, entity, Void.class);
    }
}
