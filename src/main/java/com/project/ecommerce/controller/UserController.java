package com.project.ecommerce.controller;

import com.project.ecommerce.dto.UserDetailsResponse;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.security.JwtUtil;
import com.project.ecommerce.service.CustomUserDetailsService;
import com.project.ecommerce.service.UserService;
import com.project.ecommerce.dto.PasswordChangeRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "https://voithucart.vercel.app")
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
        user.setRole("ROLE_CUSTOMER");
        userRepository.save(user);
        return "Customer registered successfully!";
    }

    // ✅ Register as Seller
    @PostMapping("/register/seller")
    public String registerSeller(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("SELLER");
        userRepository.save(user);
        return "Seller registered successfully!";
    }

    // ✅ Login (generate token and return role)
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                String token = jwtUtil.generateToken(userDetails);

                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("role", existingUser.getRole());
                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!");
    }

    // ✅ View email from JWT token (test only)
    @GetMapping("/profile")
    public String getUserProfile(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);
        return "Hello, your email is: " + email;
    }

    // ✅ New: Logged-in user’s details (for profile)
    @GetMapping("/me")
    public ResponseEntity<UserDetailsResponse> getCurrentUserDetails(Authentication authentication) {
        String email = authentication.getName(); // Extracted from token

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a response DTO to include necessary fields (without password)
        UserDetailsResponse response = new UserDetailsResponse(
                user.getName(),
                user.getPhone_number(),
                user.getAddress(),
                user.getEmail() // Adding email field
        );

        return ResponseEntity.ok(response);
    }

    // ✅ Update User Details (excluding password)
    @PutMapping("/me")
    public ResponseEntity<String> updateUserDetails(@RequestBody User updatedUser, Authentication authentication) {
        String email = authentication.getName(); // Extracted from token

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update non-sensitive fields
        user.setName(updatedUser.getName());
        user.setPhone_number(updatedUser.getPhone_number());
        user.setAddress(updatedUser.getAddress());

        userRepository.save(user);

        return ResponseEntity.ok("User details updated successfully");
    }

    // ✅ Change Password
    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordChangeRequest passwordChangeRequest, Authentication authentication) {
        String email = authentication.getName(); // Extracted from token

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify the current password
        if (!passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password updated successfully");
    }

    // ✅ ADMIN: Get all users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // ✅ ADMIN: Test admin access
    @GetMapping("/admin-test")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdminAccess() {
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
