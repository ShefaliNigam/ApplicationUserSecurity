package com.application.security.test.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.application.security.exception.NoDetailsException;
import com.application.security.jwt.Entity.JwtRequest;
import com.application.security.jwt.controller.JwtController;
import com.application.security.jwt.helper.JwtUtil;
import com.application.security.jwt.service.JwtCustomUserDetailsService;

public class JwtControllerTest {

    @InjectMocks
    private JwtController jwtController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtCustomUserDetailsService jwtCustomUserDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateToken_Success() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("username", "password");
        UserDetails userDetails = new User("username", "password", new ArrayList<>());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String token = "generated_token";

        when(authenticationManager.authenticate(any()))
                .thenReturn(null); // Simulate successful authentication
        when(jwtCustomUserDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(token);

        ResponseEntity<?> responseEntity = jwtController.generateToken(jwtRequest);

        assertEquals(ResponseEntity.ok(token), responseEntity);
    }

//    //(expected = BadCredentialsException.class)
//    @Test 
//    public void testGenerateToken_BadCredentialsException() throws Exception {
//        JwtRequest jwtRequest = new JwtRequest("username", "password");
//
////        when(authenticationManager.authenticate(any()))
////                .thenThrow(new BadCredentialsException("Bad Credentials"));
//
//        when(authenticationManager.authenticate(any()))
//        .thenReturn(new UsernamePasswordAuthenticationToken("username", "password"));
//
//        jwtController.generateToken(jwtRequest);
//    }

    //(expected = NoDetailsException.class)
    @Test
    public void testGenerateToken_NoDetailsException() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("username", "password");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new NoDetailsException("No Details"));

        jwtController.generateToken(jwtRequest);
    }

    //(expected = UsernameNotFoundException.class)
//    @Test
//    public void testGenerateToken_UsernameNotFoundException() throws Exception {
//        JwtRequest jwtRequest = new JwtRequest("username", "password");
//
//        when(authenticationManager.authenticate(any()))
//                .thenThrow(new UsernameNotFoundException("Username not found"));
//
//        jwtController.generateToken(jwtRequest);
//    }
}
