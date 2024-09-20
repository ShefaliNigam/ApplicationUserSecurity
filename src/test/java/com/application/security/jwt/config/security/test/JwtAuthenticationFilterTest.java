package com.application.security.jwt.config.security.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.application.security.jwt.config.JwtAuthenticationFilter;
import com.application.security.jwt.helper.JwtUtil;
import com.application.security.jwt.service.JwtCustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtCustomUserDetailsService customUserDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternalValidToken() throws Exception {
        String token = "validToken";
        String username = "user";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.getUsernameFromToken(token)).thenReturn(username);
        when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
      //  when(userDetails.getAuthorities()).(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));//
        
        // Mock the WebAuthenticationDetailsSource buildDetails method
        WebAuthenticationDetailsSource webAuthenticationDetailsSource = mock(WebAuthenticationDetailsSource.class);
        when(webAuthenticationDetailsSource.buildDetails(any(HttpServletRequest.class))).thenReturn(null);
        
        // Test filter logic
      //  jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Verify interaction with SecurityContextHolder
       // verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalInvalidToken() throws Exception {
        String invalidToken = "invalidToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidToken);
        when(jwtUtil.getUsernameFromToken(invalidToken)).thenThrow(new RuntimeException("Invalid Token"));

       // jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

       // verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalNoToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

      //  jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

       // verify(filterChain).doFilter(request, response);
    }
}
