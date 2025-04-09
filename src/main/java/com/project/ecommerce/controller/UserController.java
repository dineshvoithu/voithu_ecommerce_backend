package com.project.ecommerce.controller;

import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.security.JwtUtil;
import com.project.ecommerce.service.CustomUserDetailsService;
import com.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    // ✅ Register as Customer
    @PostMapping("/register/customer")
    public String registerCustomer(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_CUSTOMER"); // Set role
        userRepository.save(user);
        return "Customer registered successfully!";
    }

    // ✅ Register as Seller
    @PostMapping("/register/seller")
    public String registerSeller(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("SELLER"); // Set role
        userRepository.save(user);
        return "Seller registered successfully!";
    }

    // ✅ Login (generate token)
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                System.out.println("🔐 Logged in as: " + user.getEmail());
                System.out.println("🔐 Role: " + existingUser.getRole());
                return jwtUtil.generateToken(userDetails);
            }
        }

        return "Invalid email or password!";
    }

    // ✅ View logged-in user's profile (using JWT token)
    @GetMapping("/profile")
    public String getUserProfile(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);
        return "Hello, your email is: " + email;
    }

    // ✅ ADMIN: Get all users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        System.out.println("✅ Inside getAllUsers() method");
        return userService.getAllUsers();
    }

    // ✅ ADMIN: Test if token has admin access
    @GetMapping("/admin-test")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdminAccess() {
        System.out.println("✅ ADMIN access confirmed!");
        return "Welcome, Admin!";
    }

    // ✅ ADMIN: Get user by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // ✅ ADMIN: Delete user by ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
