package com.project.ecommerce.service;

import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 🔹 Admin: Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 🔹 Admin: Get a user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 🔹 Admin: Delete user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
