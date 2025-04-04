package com.project.ecommerce.controller;

import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ User Registration API
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userRepository.save(user);
        return "User registered successfully!";
    }

    // ✅ User Login API (Now returns JWT Token)
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        // Find user by email
        User existingUser = userRepository.findByEmail(user.getEmail());

        // Check if user exists and password matches
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return jwtUtil.generateToken(user.getEmail()); // Return JWT Token
        } else {
            return "Invalid email or password!";
        }
    }
}
