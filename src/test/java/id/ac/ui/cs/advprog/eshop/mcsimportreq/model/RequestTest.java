package id.ac.ui.cs.advprog.eshop.mcsimportreq.model;

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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(-50.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Price cannot be negative or zero", exception.getMessage());
    }

    @Test
    void testSetPriceThrowsExceptionWhenZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(0.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Price cannot be negative or zero", exception.getMessage());
    }

    @Test
    void testSetPriceThrowsExceptionWhenNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(null)
                    .setStoreUrl("http://example.com")
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Price cannot be negative or zero", exception.getMessage());
    }

    // Test for equality methods
    @Test
    void testEqualsWithSameId() {
        UUID id = UUID.randomUUID();
        Request request1 = new Request.Builder()
                .setId(id)
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        Request request2 = new Request.Builder()
                .setId(id)
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        assertEquals(request1, request2, "Objects with the same id should be equal");
    }

    @Test
    void testHashCodeForEqualObjects() {
        UUID id = UUID.randomUUID();
        Request request1 = new Request.Builder()
                .setId(id)
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        Request request2 = new Request.Builder()
                .setId(id)
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        assertEquals(request1.hashCode(), request2.hashCode(), "Hash codes should be equal for equal objects");
    }

    // Test for non-ID equality (should be unequal due to different IDs)
    @Test
    void testEqualsWithDifferentId() {
        Request request1 = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        Request request2 = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        assertNotEquals(request1, request2, "Objects with different ids should not be equal");
    }

    // Valid input test
    @Test
    void testValidRequestCreation() {
        UUID id = UUID.randomUUID();
        Request request = new Request.Builder()
                .setId(id)
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName(null)
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(100.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Product name cannot be empty", exception.getMessage());
    }

    @Test
    void testSetProductNameThrowsExceptionWhenEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(100.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Product name cannot be empty", exception.getMessage());
    }

    @Test
    void testSetProductNameSuccessfullyWhenValid() {
        String validProductName = "PlayStation 5";
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName(validProductName)
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        assertEquals(validProductName, request.getProductName());
    }

    // Setter tests for imageUrl
    @Test
    void testSetImageUrlSuccessfully() {
        String validImageUrl = "http://example.com/new_image.jpg";
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl(validImageUrl)
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        assertEquals(validImageUrl, request.getImageUrl());
    }

    @Test
    void testSetImageUrlCannotBeNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl(null)
                    .setPrice(100.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Image URL cannot be empty", exception.getMessage());
    }

    @Test
    void testSetImageUrlCannotBeEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("")
                    .setPrice(100.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Image URL cannot be empty", exception.getMessage());
    }

    // Setter tests for price
    @Test
    void testSetPriceSuccessfully() {
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(150.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        assertEquals(150.0, request.getPrice());
    }

    // Setter tests for storeUrl
    @Test
    void testSetStoreUrlSuccessfully() {
        String validStoreUrl = "http://example.com/new_store";
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl(validStoreUrl)
                .setCurrency("USD")
                .build();

        assertEquals(validStoreUrl, request.getStoreUrl());
    }

    @Test
    void testSetStoreUrlCannotBeNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(100.0)
                    .setStoreUrl(null)
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Store URL cannot be empty", exception.getMessage());
    }

    @Test
    void testSetStoreUrlCannotBeEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(100.0)
                    .setStoreUrl("")
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Store URL cannot be empty", exception.getMessage());
    }

    // Setter tests for currency
    @Test
    void testSetCurrencySuccessfully() {
        String validCurrency = "EUR";
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency(validCurrency)
                .build();

        assertEquals(validCurrency, request.getCurrency());
    }

    @Test
    void testSetCurrencyCannotBeNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(100.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency(null)
                    .build();
        });
        assertEquals("Currency cannot be empty", exception.getMessage());
    }

    @Test
    void testSetCurrencyCannotBeEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(100.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency("")
                    .build();
        });
        assertEquals("Currency cannot be empty", exception.getMessage());
    }

    @Test
    void testInequality() {
        Request request1 = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        Request request2 = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Mirano Lacth")
                .setImageUrl("http://example.com/image1.jpg")
                .setPrice(106.0)
                .setStoreUrl("http://example1.com")
                .setCurrency("USD")
                .build();

        assertNotEquals(request1, request2);
    }

    @Test
    void testHashCodeConsistency() {
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        int initialHashCode = request.hashCode();
        int secondHashCode = request.hashCode();
        assertEquals(initialHashCode, secondHashCode, "Hash code should be consistent");
    }

    @Test
    void testHashCodeInequalityForDifferentObjects() {
        Request request1 = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        Request request2 = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("PlayStation 5")
                .setImageUrl("http://example.com/image2.jpg")
                .setPrice(200.0)
                .setStoreUrl("http://example.com/store2")
                .setCurrency("EUR")
                .build();

        assertNotEquals(request1.hashCode(), request2.hashCode(), "Hash codes should be different for different objects");
    }

    @Test
    void testEqualsWithNull() {
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        assertNotEquals(request, null);
    }

    @Test
    void testEqualsWithDifferentObjectType() {
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        String differentObject = "I am not a Request object";
        assertNotEquals(request, differentObject);
    }

    @Test
    void testEqualsWithSameObject() {
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        assertEquals(request, request); // should be true
    }

    @Test
    void testEqualsWithSameFieldsButDifferentIds() {
        Request request1 = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        request1.setId(UUID.randomUUID());

        Request request2 = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        request2.setId(UUID.randomUUID());

        assertNotEquals(request1, request2, "Objects with different ids should not be equal");
    }

    @Test
    void testEqualsWithOneNullId() {
        Request request1 = new Request.Builder()
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        request1.setId(null);

        Request request2 = new Request.Builder()
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        request2.setId(UUID.randomUUID());

        assertNotEquals(request1, request2);
    }

    @Test
    void testEqualsWithBothNullIds() {
        Request request1 = new Request.Builder()
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        request1.setId(null);

        Request request2 = new Request.Builder()
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        request2.setId(null);

        assertEquals(request1, request2, "Objects with null ids should be equal");
    }

    // Additional setter tests
    @Test
    void testSetId() {
        UUID id = UUID.randomUUID();
        Request request = new Request.Builder()
                .setId(id)
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        assertEquals(id, request.getId());
    }

    @Test
    void testSetProductName() {
        String productName = "PlayStation 5";
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName(productName)
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        assertEquals(productName, request.getProductName());
    }

    @Test
    void testSetCurrencyThrowsExceptionWhenEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(100.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency("")
                    .build();
        });
        assertEquals("Currency cannot be empty", exception.getMessage());
    }

    @Test
    void testSetCurrencySuccessfullyWhenValid() {
        String validCurrency = "IDR";
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency(validCurrency)
                .build();

        assertEquals(validCurrency, request.getCurrency());
    }

    @Test
    void testSetCurrencyThrowsExceptionWhenNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(100.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency(null)
                    .build();
        });
        assertEquals("Currency cannot be empty", exception.getMessage());
    }

    @Test
    void testSetStoreUrlThrowsExceptionWhenEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(100.0)
                    .setStoreUrl("")
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Store URL cannot be empty", exception.getMessage());
    }

    @Test
    void testSetStoreUrlSuccessfullyWhenValid() {
        String validStoreUrl = "http://example.com/new_store";
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl(validStoreUrl)
                .setCurrency("USD")
                .build();

        assertEquals(validStoreUrl, request.getStoreUrl());
    }

    @Test
    void testSetStoreUrlThrowsExceptionWhenNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("http://example.com/image.jpg")
                    .setPrice(100.0)
                    .setStoreUrl(null)
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Store URL cannot be empty", exception.getMessage());
    }

    // Setter tests for imageUrl
    @Test
    void testSetImageUrlThrowsExceptionWhenNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl(null)
                    .setPrice(100.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Image URL cannot be empty", exception.getMessage());
    }

    @Test
    void testSetImageUrlThrowsExceptionWhenEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Request.Builder()
                    .setId(UUID.randomUUID())
                    .setProductName("Nintendo Switch")
                    .setImageUrl("")
                    .setPrice(100.0)
                    .setStoreUrl("http://example.com")
                    .setCurrency("USD")
                    .build();
        });
        assertEquals("Image URL cannot be empty", exception.getMessage());
    }

    @Test
    void testSetImageUrlSuccessfullyWhenValid() {
        String validImageUrl = "http://example.com/newimage.jpg";
        Request request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl(validImageUrl)
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        assertEquals(validImageUrl, request.getImageUrl());
    }

}