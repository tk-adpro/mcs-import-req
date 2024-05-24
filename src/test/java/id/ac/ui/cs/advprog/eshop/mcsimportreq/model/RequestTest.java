package id.ac.ui.cs.advprog.eshop.mcsimportreq.model;

import io.micrometer.common.util.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {
    // Test for no-arg constructor
    @Test
    void testNoArgConstructor() {
        Request request = new Request();
        assertNotNull(request);
    }

    // Test for invalid price values (negative, zero, null)
    @Test
    void testSetPriceThrowsExceptionWhenNegative() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setPrice(-50.0);
        });
        assertEquals("Price cannot be negative or zero", exception.getMessage());
    }

    @Test
    void testSetPriceThrowsExceptionWhenZero() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setPrice(0.0);
        });
        assertEquals("Price cannot be negative or zero", exception.getMessage());
    }

    @Test
    void testSetPriceThrowsExceptionWhenNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setPrice(null);
        });
        assertEquals("Price cannot be negative or zero", exception.getMessage());
    }

    // Test for equality methods
    @Test
    void testEqualsWithSameId() {
        UUID id = UUID.randomUUID();
        Request request1 = new Request(id, "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Request request2 = new Request(id, "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");

        assertEquals(request1, request2, "Objects with the same id should be equal");
    }

    @Test
    void testHashCodeForEqualObjects() {
        UUID id = UUID.randomUUID();
        Request request1 = new Request(id, "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Request request2 = new Request(id, "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");

        assertEquals(request1.hashCode(), request2.hashCode(), "Hash codes should be equal for equal objects");
    }

    // Test for non-ID equality (should be unequal due to different IDs)
    @Test
    void testEqualsWithDifferentId() {
        Request request1 = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Request request2 = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");

        assertNotEquals(request1, request2, "Objects with different ids should not be equal");
    }

    // Valid input test
    @Test
    void testValidRequestCreation() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        assertNotNull(request);
        assertEquals("Nintendo Switch", request.getProductName());
        assertEquals("http://example.com/image.jpg", request.getImageUrl());
        assertEquals(100.0, request.getPrice());
        assertEquals("http://example.com", request.getStoreUrl());
        assertEquals("USD", request.getCurrency());
    }

    // Setter tests for productName
    @Test
    void testSetProductNameThrowsExceptionWhenNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setProductName(null);
        });
        assertEquals("Product name cannot be empty", exception.getMessage());
    }

    @Test
    void testSetProductNameThrowsExceptionWhenEmpty() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setProductName("");
        });
        assertEquals("Product name cannot be empty", exception.getMessage());
        assertFalse(StringUtils.isEmpty(request.getProductName()));
    }

    @Test
    void testSetProductNameSuccessfullyWhenValid() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        String validProductName = "PlayStation 5";
        request.setProductName(validProductName);
        assertEquals(validProductName, request.getProductName());
    }

    @Test
    void testSetProductNameSuccessfullyWhenNotEmptyAndNotNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        String validProductName = "PlayStation 5";
        request.setProductName(validProductName);
        assertNotNull(request.getProductName());
        assertFalse(StringUtils.isEmpty(request.getProductName()));
    }

    // Setter tests for imageUrl
    @Test
    void testSetImageUrl() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        request.setImageUrl("http://example.com/new_image.jpg");
        assertEquals("http://example.com/new_image.jpg", request.getImageUrl());
    }

    @Test
    void testSetImageUrlCannotBeNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setImageUrl(null);
        });
        assertEquals("Image URL cannot be empty", exception.getMessage());
    }

    @Test
    void testSetImageUrlCannotBeEmpty() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setImageUrl("");
        });
        assertEquals("Image URL cannot be empty", exception.getMessage());
    }

    @Test
    void testSetImageUrlSuccessfullyWhenNotEmptyAndNotNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        String validImageUrl = "http://example.com/newimage.jpg";
        request.setImageUrl(validImageUrl);
        assertNotNull(request.getImageUrl());
        assertFalse(StringUtils.isEmpty(request.getImageUrl()));
    }

    // Setter tests for price
    @Test
    void testSetPrice() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        request.setPrice(150.0);
        assertEquals(150.0, request.getPrice());
    }

    @Test
    void testSetPriceCannotBeNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setPrice(null);
        });
        assertEquals("Price cannot be negative or zero", exception.getMessage());
    }

    @Test
    void testSetPriceCannotBeNegative() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setPrice(-100.0);
        });
        assertEquals("Price cannot be negative or zero", exception.getMessage());
    }

    @Test
    void testSetPriceCannotBeZero() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setPrice(0.0);
        });
        assertEquals("Price cannot be negative or zero", exception.getMessage());
    }

    // Setter tests for storeUrl
    @Test
    void testSetStoreUrl() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        request.setStoreUrl("http://example.com/new_store");
        assertEquals("http://example.com/new_store", request.getStoreUrl());
    }

    @Test
    void testSetStoreUrlCannotBeNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setStoreUrl(null);
        });
        assertEquals("Store URL cannot be empty", exception.getMessage());
    }

    @Test
    void testSetStoreUrlCannotBeEmpty() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setStoreUrl("");
        });
        assertEquals("Store URL cannot be empty", exception.getMessage());
    }

    // Setter tests for currency
    @Test
    void testSetCurrency() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        request.setCurrency("EUR");
        assertEquals("EUR", request.getCurrency());
        assertTrue(StringUtils.isNotEmpty(request.getCurrency()));
        assertTrue(StringUtils.isNotBlank(request.getCurrency()));
    }

    @Test
    void testSetCurrencyCannotBeNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setCurrency(null);
        });
        assertEquals("Currency cannot be empty", exception.getMessage());
    }

    @Test
    void testSetCurrencyCannotBeEmpty() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setCurrency("");
        });
        assertEquals("Currency cannot be empty", exception.getMessage());
    }

    @Test
    void testInequality() {
        Request request1 = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Request request2 = new Request(UUID.randomUUID(), "Mirano Lacth", "http://example.com/image1.jpg", 106.0, "http://example1.com", "USD");
        assertNotEquals(request1, request2);
    }

    @Test
    void testHashCodeConsistency() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        int initialHashCode = request.hashCode();
        int secondHashCode = request.hashCode();
        assertEquals(initialHashCode, secondHashCode, "Hash code should be consistent");
    }

    @Test
    void testHashCodeInequalityForDifferentObjects() {
        Request request1 = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Request request2 = new Request(UUID.randomUUID(), "PlayStation 5", "http://example.com/image2.jpg", 200.0, "http://example.com/store2", "EUR");
        assertNotEquals(request1.hashCode(), request2.hashCode(), "Hash codes should be different for different objects");
    }

    @Test
    void testHashCodeInequalityForDifferentObjects2() {
        Request request1 = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Request request2 = new Request(UUID.randomUUID(), "PlayStation 5", "http://example.com/image2.jpg", 200.0, "http://example.com/store2", "EUR");
        assertFalse(request1.hashCode() == request2.hashCode(), "Hash codes should be different for different objects");
    }

    @Test
    void testEqualsWithNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        assertNotEquals(request, null);
    }

    @Test
    void testEqualsWithDifferentObjectType() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        String differentObject = "I am not a Request object";
        assertNotEquals(request, differentObject);
    }

    @Test
    void testEqualsWithSameObject() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        assertEquals(request, request); // should be true
    }

    @Test
    void testEqualsWithSameFieldsButDifferentIds() {
        Request request1 = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        request1.setId(UUID.randomUUID());
        Request request2 = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        request2.setId(UUID.randomUUID());

        assertNotEquals(request1, request2, "Objects with different ids should not be equal");
    }

    @Test
    void testEqualsWithOneNullId() {
        Request request1 = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        request1.setId(null);
        Request request2 = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        request2.setId(UUID.randomUUID());

        assertNotEquals(request1, request2);
    }

    @Test
    void testEqualsWithBothNullIds() {
        Request request1 = new Request(null, "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Request request2 = new Request(null, "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");

        assertEquals(request1, request2, "Objects with null ids should be equal");
    }

    // Additional setter tests
    @Test
    void testSetId() {
        Request request = new Request();
        UUID id = UUID.randomUUID();
        request.setId(id);
        assertEquals(id, request.getId());
    }

    @Test
    void testSetProductName() {
        Request request = new Request();
        String productName = "PlayStation 5";
        request.setProductName(productName);
        assertEquals(productName, request.getProductName());
    }

    @Test
    void testSetCurrencyThrowsExceptionWhenEmpty() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setCurrency("");
        });
        assertEquals("Currency cannot be empty", exception.getMessage());
    }

    @Test
    void testSetCurrencySuccessfullyWhenValid() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        String validCurrency = "IDR";
        request.setCurrency(validCurrency);
        assertEquals(validCurrency, request.getCurrency());
    }

    @Test
    void testSetCurrencyThrowsExceptionWhenNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setCurrency(null);
        });
        assertEquals("Currency cannot be empty", exception.getMessage());
    }

    @Test
    void testSetStoreUrlThrowsExceptionWhenEmpty() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setStoreUrl("");
        });
        assertEquals("Store URL cannot be empty", exception.getMessage());
    }

    @Test
    void testSetStoreUrlSuccessfullyWhenValid() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        String validStoreUrl = "http://example.com/new_store";
        request.setStoreUrl(validStoreUrl);
        assertEquals(validStoreUrl, request.getStoreUrl());
    }

    @Test
    void testSetStoreUrlThrowsExceptionWhenNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setStoreUrl(null);
        });
        assertEquals("Store URL cannot be empty", exception.getMessage());
    }

    // Setter tests for imageUrl
    @Test
    void testSetImageUrlThrowsExceptionWhenNull() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setImageUrl(null);
        });
        assertEquals("Image URL cannot be empty", exception.getMessage());
    }

    @Test
    void testSetImageUrlThrowsExceptionWhenEmpty() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            request.setImageUrl("");
        });
        assertEquals("Image URL cannot be empty", exception.getMessage());
    }

    @Test
    void testSetImageUrlSuccessfullyWhenValid() {
        Request request = new Request(UUID.randomUUID(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        String validImageUrl = "http://example.com/newimage.jpg";
        request.setImageUrl(validImageUrl);
        assertEquals(validImageUrl, request.getImageUrl());
    }
}
