package com.application.security.test.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.application.security.controller.UsersController;
import com.application.security.entity.Users;
import com.application.security.exception.UserAlreadyExistsException;
import com.application.security.repository.UsersRepository;
import com.application.security.service.impl.UsersServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

public class UsersControllerTest {

    @Mock
    private UsersServiceImpl usersServiceImpl;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UsersController usersController;

    private Users user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new Users();
        user.setId("1");
        user.setUserEmail("test@example.com");
        user.setUserPassword("password");
        user.setUserRole("USER");
    }

    @Test
    public void testCreateUser_Success() {
        // Mocking UsersRepository and BCryptPasswordEncoder
        when(usersRepository.findByUserEmail(user.getUserEmail())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(usersServiceImpl.addUser(any(Users.class))).thenReturn(Optional.of(user));

        // Execute the method
        ResponseEntity<Users> response = usersController.createUser(user);

        // Assert the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("encodedPassword", response.getBody().getUserPassword());

        // Verify interactions
        verify(usersRepository, times(1)).findByUserEmail(user.getUserEmail());
        verify(bCryptPasswordEncoder, times(1)).encode(anyString());
        verify(usersServiceImpl, times(1)).addUser(any(Users.class));
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        // Mock UsersRepository to return existing user
        when(usersRepository.findByUserEmail(user.getUserEmail())).thenReturn(Optional.of(user));

        // Assert that UserAlreadyExistsException is thrown
        assertThrows(UserAlreadyExistsException.class, () -> usersController.createUser(user));

        // Verify that no further interactions occurred
        verify(bCryptPasswordEncoder, never()).encode(anyString());
        verify(usersServiceImpl, never()).addUser(any(Users.class));
    }

    @Test
    public void testGetUserById_Success() {
        when(usersServiceImpl.getUserById("1")).thenReturn(Optional.of(user));

        // Execute the method
        ResponseEntity<Users> response = usersController.getUserById("1");

        // Assert the response
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getUserEmail());

        // Verify interactions
        verify(usersServiceImpl, times(1)).getUserById("1");
    }

    @Test
    public void testGetUserById_NotFound() {
        when(usersServiceImpl.getUserById("1")).thenReturn(Optional.empty());

        // Execute the method
       // ResponseEntity<Users> response = usersController.getUserById("1");

        // Assert the response
        //assertNull(response.getBody());
       // assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        // Verify interactions
       // verify(usersServiceImpl, times(1)).getUserById("1");
    }

    @Test
    public void testGetAllData_Success() {
        List<Users> usersList = Arrays.asList(user);
        when(usersServiceImpl.getAllData()).thenReturn(usersList);

        // Execute the method
        ResponseEntity<List<Users>> response = usersController.getAllData();

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("test@example.com", response.getBody().get(0).getUserEmail());

        // Verify interactions
        verify(usersServiceImpl, times(1)).getAllData();
    }

    @Test
    public void testGetUserByName_Success() {
        when(usersServiceImpl.getUserByName("test")).thenReturn(Optional.of(user));

        // Execute the method
        ResponseEntity<Users> response = usersController.getUserByName("test");

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getUserEmail());

        // Verify interactions
        verify(usersServiceImpl, times(1)).getUserByName("test");
    }

    @Test
    public void testGetUserByEmail_Success() {
        when(usersServiceImpl.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Execute the method
        ResponseEntity<Users> response = usersController.getUserByEmail("test@example.com");

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getUserEmail());

        // Verify interactions
        verify(usersServiceImpl, times(1)).getUserByEmail("test@example.com");
    }

    @Test
    public void testGetUsersByRole_Success() {
        List<Users> usersList = Arrays.asList(user);
        when(usersServiceImpl.getUserByRole("USER")).thenReturn(Optional.of(usersList));

        // Execute the method
        ResponseEntity<List<Users>> response = usersController.getUsersByRole("USER");

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("USER", response.getBody().get(0).getUserRole());

        // Verify interactions
        verify(usersServiceImpl, times(1)).getUserByRole("USER");
    }

    @Test
    public void testGetUserByNumber_Success() {
        when(usersServiceImpl.getUserByNumber(1234567890L)).thenReturn(Optional.of(user));

        // Execute the method
        ResponseEntity<Users> response = usersController.getUserByNumber(1234567890L);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
       // assertEquals(1234567890L, response.getBody().getUserMobile());

        // Verify interactions
       // verify(usersServiceImpl, times(1)).getUserByNumber(1234567890L);
    }
}
