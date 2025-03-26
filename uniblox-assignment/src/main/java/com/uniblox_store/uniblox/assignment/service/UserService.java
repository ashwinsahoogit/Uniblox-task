package com.uniblox_store.uniblox.assignment.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.uniblox_store.uniblox.assignment.model.User;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public User registerUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(String userId) {
        return users.get(userId);
    }
}
