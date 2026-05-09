package com.surfapi.surfobservationapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
    
    private final JwtUtil jwtUtil;

    // inject JwtUtil for token validation and extraction
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)

            throws ServletException, IOException {

                // get Authorization header from request
                String authHeader = request.getHeader("Authorization");

                // skip if header is missing or doesn't contain Bearer token
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    filterChain.doFilter(request, response);
                    return;
                }

                // extract token after "Bearer "
                String token = authHeader.substring(7);

                // skip authentication if token is invalid
                if (!jwtUtil.isTokenValid(token)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                // extract user details form otken
                Long userId = jwtUtil.extractUserId(token);
                String email = jwtUtil.extractEmail(token);

                // create authenticated user object
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, email, List.of());

                // store authentication in security context
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // continue request processing
                filterChain.doFilter(request, response);
            }
}
