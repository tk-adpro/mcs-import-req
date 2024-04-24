package id.ac.ui.cs.advprog.eshop.mcsimportreq.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {

    @Test
    void testProductNameCannotBeEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request("", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        });
        assertEquals("Product name cannot be empty", exception.getMessage());
    }

    @Test
    void testPriceCannotBeNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request("Nintendo Switch", "http://example.com/image.jpg", -100.0, "http://example.com", "USD");
        });
        assertEquals("Price cannot be negative or zero", exception.getMessage());
    }

    @Test
    void testProductNameCannotBeNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request(null, "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        });
        assertEquals("Product name cannot be empty", exception.getMessage()); // Ubah pesan yang diharapkan menjadi "Product name cannot be null"
    }

    @Test
    void testImageUrlCannotBeEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request("Nintendo Switch", "", 100.0, "http://example.com", "USD");
        });
        assertEquals("Image URL cannot be empty", exception.getMessage());
    }

    @Test
    void testImageUrlCannotBeNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request("Nintendo Switch", null, 100.0, "http://example.com", "USD");
        });
        assertEquals("Image URL cannot be empty", exception.getMessage());
    }

    @Test
    void testStoreUrlCannotBeEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "", "USD");
        });
        assertEquals("Store URL cannot be empty", exception.getMessage());
    }

    @Test
    void testStoreUrlCannotBeNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, null, "USD");
        });
        assertEquals("Store URL cannot be empty", exception.getMessage());
    }

    @Test
    void testCurrencyCannotBeEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "");
        });
        assertEquals("Currency cannot be empty", exception.getMessage());
    }

    @Test
    void testCurrencyCannotBeNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", null);
        });
        assertEquals("Currency cannot be empty", exception.getMessage());
    }

    @Test
    void testAllValuesCannotBeEmpty() {
        // Expecting IllegalArgumentException because all values are empty or null
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request("", "", -100.0, "", "");
        });
        assertEquals("Product name cannot be empty", exception.getMessage());
    }
}
