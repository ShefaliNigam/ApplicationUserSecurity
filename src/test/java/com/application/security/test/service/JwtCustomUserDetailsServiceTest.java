package com.application.security.test.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.application.security.entity.Users;
import com.application.security.jwt.service.JwtCustomUserDetails;
import com.application.security.jwt.service.JwtCustomUserDetailsService;
import com.application.security.repository.UsersRepository;

public class JwtCustomUserDetailsServiceTest {

    @InjectMocks
    private JwtCustomUserDetailsService jwtCustomUserDetailsService;

    @Mock
    private UsersRepository usersRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @org.junit.jupiter.api.Test
    public void testLoadUserByUsername_UserFound() {
        String userEmail = "test@example.com";
        Users user = new Users();
        user.setUserEmail(userEmail);
        user.setUserPassword("password");

        Optional<Users> optionalUser = Optional.of(user);

        when(usersRepository.findByUserEmail(userEmail)).thenReturn(optionalUser);

        UserDetails userDetails = jwtCustomUserDetailsService.loadUserByUsername(userEmail);

        assertEquals(userEmail, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    //(expected = UsernameNotFoundException.class)
    @org.junit.jupiter.api.Test
    public void testLoadUserByUsername_UserNotFound() {
        String userEmail = "nonexistent@example.com";

        when(usersRepository.findByUserEmail(userEmail)).thenReturn(Optional.empty());

        jwtCustomUserDetailsService.loadUserByUsername(userEmail);
    }
}
