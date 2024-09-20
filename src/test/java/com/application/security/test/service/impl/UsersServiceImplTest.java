package com.application.security.test.service.impl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import com.application.security.entity.StockEntity;
import com.application.security.entity.Users;
import com.application.security.repository.UsersRepository;
import com.application.security.service.impl.UsersServiceImpl;

class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UsersServiceImpl usersServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() {
        Users user = new Users();
        user.setId("1");
        user.setUserEmail("test@example.com");

        when(usersRepository.findById("1")).thenReturn(Optional.of(user));

        Optional<Users> result = usersServiceImpl.getUserById("1");

        assertEquals("test@example.com", result.get().getUserEmail());
    }

    @Test
    void testGetUserByName() {
        Users user = new Users();
        user.setUserName("John");

        when(usersRepository.findByUserName("John")).thenReturn(Optional.of(user));

        Optional<Users> result = usersServiceImpl.getUserByName("John");

        assertEquals("John", result.get().getUserName());
    }

    @Test
    void testGetUserByEmail() {
        Users user = new Users();
        user.setUserEmail("test@example.com");

        when(usersRepository.findByUserEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<Users> result = usersServiceImpl.getUserByEmail("test@example.com");

        assertEquals("test@example.com", result.get().getUserEmail());
    }

    @Test
    void testGetUserByNumber() {
        Users user = new Users();
        user.setUserMobile(1234567890L);

        when(usersRepository.findByUserMobile(1234567890L)).thenReturn(Optional.of(user));

        Optional<Users> result = usersServiceImpl.getUserByNumber(1234567890L);

        assertEquals(1234567890L, result.get().getUserMobile());
    }

    @Test
    void testGetUserByRole() {
        Users user1 = new Users();
        user1.setUserRole("USER");

        Users user2 = new Users();
        user2.setUserRole("USER");

        List<Users> usersList = new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);

        when(usersRepository.findByUserRole("USER")).thenReturn(Optional.of(usersList));

        Optional<List<Users>> result = usersServiceImpl.getUserByRole("USER");

        assertEquals(2, result.get().size());
        assertEquals("USER", result.get().get(0).getUserRole());
    }

    @Test
    void testGetAllData() {
        Users user1 = new Users();
        user1.setUserEmail("test1@example.com");

        Users user2 = new Users();
        user2.setUserEmail("test2@example.com");

        List<Users> usersList = new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);

        when(usersRepository.findAll()).thenReturn(usersList);

        List<Users> result = usersServiceImpl.getAllData();

        assertEquals(2, result.size());
        assertEquals("test1@example.com", result.get(0).getUserEmail());
    }

    @Test
    void testGetUserStocks() {
        StockEntity stock1 = new StockEntity();
        StockEntity stock2 = new StockEntity();

        StockEntity[] stockArray = { stock1, stock2 };

        // Mocking RestTemplate for fetching stocks
        when(restTemplate.getForObject(any(String.class), any(Class.class)))
                .thenReturn(stockArray);

        List<StockEntity> result = usersServiceImpl.getUserStocks("1");

        assertEquals(2, result.size());
    }

    @Test
    void testGetStockById() {
        StockEntity stock = new StockEntity();

        when(restTemplate.getForObject(any(String.class), any(Class.class)))
                .thenReturn(stock);

        StockEntity result = usersServiceImpl.getStockById(1L);
    }
}
