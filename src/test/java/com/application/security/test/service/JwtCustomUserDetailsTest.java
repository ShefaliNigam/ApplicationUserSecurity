package com.application.security.test.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.application.security.entity.Users;
import com.application.security.jwt.service.JwtCustomUserDetails;

public class JwtCustomUserDetailsTest {

    private JwtCustomUserDetails userDetails;
    private Users user;

    @BeforeEach
    public void setUp() {
        user = new Users();
        user.setUserEmail("test@example.com");
        user.setUserPassword("password");
        user.setUserRole("ROLE_USER");

        userDetails = new JwtCustomUserDetails(Optional.of(user));
    }

    @org.junit.jupiter.api.Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @org.junit.jupiter.api.Test
    public void testGetPassword() {
        assertEquals("password", userDetails.getPassword());
    }

    @org.junit.jupiter.api.Test
    public void testGetUsername() {
        assertEquals("test@example.com", userDetails.getUsername());
    }

    @org.junit.jupiter.api.Test
    public void testIsAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @org.junit.jupiter.api.Test
    public void testIsAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @org.junit.jupiter.api.Test
    public void testIsCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @org.junit.jupiter.api.Test
    public void testIsEnabled() {
        assertTrue(userDetails.isEnabled());
    }
}

