package com.project.ecommerce.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Check if header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix

            String username = jwtUtil.extractUsername(token); // Decode username from token

            // Check if username is not null and user is not already authenticated
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Here you can also validate user from DB if needed

                // Create authentication manually
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, null);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
