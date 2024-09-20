package com.application.security.test.entity;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.application.security.entity.StockEntity;

class StockEntityTest {

    private StockEntity stock;

    @BeforeEach
    void setUp() {
        // Initialize a StockEntity object before each test case
        stock = new StockEntity(1L, "Apple", 150.0, "NASDAQ", "AAPL", 100, 123);
    }

    @Test
    void testConstructor() {
        // Test constructor initialization
        assertEquals(1L, stock.getId());
        assertEquals("Apple", stock.getName());
        assertEquals(150.0, stock.getPrice());
        assertEquals("NASDAQ", stock.getExchange());
        assertEquals("AAPL", stock.getSymbol());
        assertEquals(100, stock.getQuantity());
        assertEquals(123, stock.getUserId());
    }

    @Test
    void testSettersAndGetters() {
        // Test setters and getters
        stock.setId(2L);
        stock.setName("Microsoft");
        stock.setPrice(250.0);
        stock.setExchange("NYSE");
        stock.setSymbol("MSFT");
        stock.setQuantity(200);
        stock.setUserId(456);

        assertEquals(2L, stock.getId());
        assertEquals("Microsoft", stock.getName());
        assertEquals(250.0, stock.getPrice());
        assertEquals("NYSE", stock.getExchange());
        assertEquals("MSFT", stock.getSymbol());
        assertEquals(200, stock.getQuantity());
        assertEquals(456, stock.getUserId());
    }

    @Test
    void testToString() {
        String expected = "StockEntity [id=1, name=Apple, price=150.0, exchange=NASDAQ, symbol=AAPL, quantity=100, userId=123]";
        assertEquals(expected, stock.toString());
    }

    @Test
    void testNoArgsConstructor() {
        // Test default constructor
        StockEntity emptyStock = new StockEntity();
        assertNotNull(emptyStock);
    }
}

