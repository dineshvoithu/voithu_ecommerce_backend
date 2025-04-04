package com.project.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private String secretKey = "DkT5Qm7xZwX9c2YpR8sN3gV4yC6fH9bJmK8pX2ZrT7YqU5dV\n"; // Change this to a stronger key!

    // Generate Token
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Validate Token
    public boolean validateToken(String token, String email) {
        return (extractEmail(token).equals(email) && !isTokenExpired(token));
    }

    // Extract Email from Token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Extract Expiry Date
    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    // Check if Token Expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Get Token Claims
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
