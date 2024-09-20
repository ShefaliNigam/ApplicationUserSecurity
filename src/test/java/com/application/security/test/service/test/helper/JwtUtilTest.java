package com.application.security.test.service.test.helper;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.application.security.jwt.helper.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
        jwtUtil.secret = "testSecret"; // Set a test secret
    }

    @org.junit.jupiter.api.Test
    public void testGetUsernameFromToken() {
        String token = createToken("testUser");
        String username = jwtUtil.getUsernameFromToken(token);
        assertEquals("testUser", username);
    }

    @org.junit.jupiter.api.Test
    public void testGetExpirationDateFromToken() {
        String token = createToken("testUser");
        Date expirationDate = jwtUtil.getExpirationDateFromToken(token);
        assertNotNull(expirationDate);
    }

    @org.junit.jupiter.api.Test
    public void testGetClaimFromToken() {
        String token = createToken("testUser");
        String username = jwtUtil.getClaimFromToken(token, Claims::getSubject);
        assertEquals("testUser", username);
    }

//    @org.junit.jupiter.api.Test
//    public void testGenerateToken() {
//        UserDetails userDetails = new User("testUser", "password", null);
//        String token = jwtUtil.generateToken(userDetails);
//        assertNotNull(token);
//    }

//    @org.junit.jupiter.api.Test
//    public void testValidateToken_ValidToken() {
//        UserDetails userDetails = new User("testUser", "password", null);
//        String token = createToken("testUser");
//        assertTrue(jwtUtil.validateToken(token, userDetails));
//    }

//    @org.junit.jupiter.api.Test
//    public void testValidateToken_InvalidToken() {
//        UserDetails userDetails = new User("testUser", "password", null);
//        String token = createToken("anotherUser");
//        assertTrue(!jwtUtil.validateToken(token, userDetails));
//    }

    // Helper method to create a JWT token
    private String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SignatureAlgorithm.HS512, jwtUtil.secret)
                .compact();
    }
}
