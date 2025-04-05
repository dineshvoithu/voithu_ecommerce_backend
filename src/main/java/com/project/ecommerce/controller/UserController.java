package com.project.ecommerce.controller;

import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.security.JwtUtil;
import com.project.ecommerce.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

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
        Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // Check password (you can add password encryption later)
            if (existingUser.getPassword().equals(user.getPassword())) {
                // ✅ Load UserDetails
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

                // ✅ Generate token using UserDetails
                return jwtUtil.generateToken(userDetails);
            }
        }

        return "Invalid email or password!";
    }

    // ✅ Secure Profile API - uses Spring Security Authentication context
    @GetMapping("/profile")
    public String getUserProfile(Authentication authentication) {
        String email = authentication.getName();
        return "Hello, your email is: " + email;
    }
}
