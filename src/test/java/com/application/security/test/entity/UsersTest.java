package com.application.security.test.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.application.security.entity.StockEntity;
import com.application.security.entity.Users;

class UsersTest {

    private Users user;
    private List<StockEntity> stocks;

    @BeforeEach
    public void setUp() {
        StockEntity stock1 = new StockEntity();
        StockEntity stock2 = new StockEntity();
        stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);

        user = new Users("1234", 1, "John Doe", "john.doe@example.com", "password123", 9876543210L, "USER", stocks);
    }

    @Test
    void testConstructor() {
        assertNotNull(user);
        assertEquals("1234", user.getId());
        assertEquals(1, user.getUserId());
        assertEquals("John Doe", user.getUserName());
        assertEquals("john.doe@example.com", user.getUserEmail());
        assertEquals("password123", user.getUserPassword());
        assertEquals(9876543210L, user.getUserMobile());
        assertEquals("USER", user.getUserRole());
        assertEquals(2, user.getStocks().size());
    }

    @Test
    void testSettersAndGetters() {
        Users newUser = new Users();
        newUser.setId("5678");
        newUser.setUserId(2);
        newUser.setUserName("Jane Doe");
        newUser.setUserEmail("jane.doe@example.com");
        newUser.setUserPassword("password456");
        newUser.setUserMobile(1234567890L);
        newUser.setUserRole("ADMIN");
        newUser.setStocks(stocks);

        assertEquals("5678", newUser.getId());
        assertEquals(2, newUser.getUserId());
        assertEquals("Jane Doe", newUser.getUserName());
        assertEquals("jane.doe@example.com", newUser.getUserEmail());
        assertEquals("password456", newUser.getUserPassword());
        assertEquals(1234567890L, newUser.getUserMobile());
        assertEquals("ADMIN", newUser.getUserRole());
        assertEquals(stocks, newUser.getStocks());
    }

    @Test
    void testToString() {
        String expected = "Users [id=1234, userId=1, userName=John Doe, userEmail=john.doe@example.com, "
                + "userPassword=password123, userMobile=9876543210, userRole=USER, stocks=" + stocks + "]";
        assertEquals(expected, user.toString());
    }
}
