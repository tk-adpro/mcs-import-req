package id.ac.ui.cs.advprog.eshop.mcsimportreq.service;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import id.ac.ui.cs.advprog.eshop.mcsimportreq.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RequestServiceImpl requestService;

    private Request request;
    private UUID requestId;

    @BeforeEach
    void setUp() {
        requestId = UUID.randomUUID();
        request = new Request.Builder()
                .setId(requestId)
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();
    }

    @Test
    void testSaveRequest_Success() {
        when(requestRepository.save(request)).thenReturn(request);

        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 14000.0);
        rates.put("JPY", 130.0);
        rates.put("IDR", 1.0); // Assuming IDR has an exchange rate of 1.0 for simplicity
        apiResponse.put("rates", rates);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        Request savedRequest = requestService.saveRequest(request);
        assertNotNull(savedRequest);
        assertEquals(request, savedRequest);
        verify(requestRepository, times(1)).save(request);
    }

    @Test
    void testSaveRequest_InvalidCurrency() {
        request.setCurrency("XYZ"); // Invalid currency code

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            requestService.saveRequest(request);
        });

        assertEquals("Invalid currency code: XYZ", exception.getMessage());
        verifyNoInteractions(requestRepository);
    }

    @Test
    void testGetRequestById_Success() {
        when(requestRepository.findRequestById(requestId)).thenReturn(Optional.of(request));

        Request foundRequest = requestService.getRequestById(requestId);

        assertNotNull(foundRequest);
        assertEquals(request, foundRequest);
        verify(requestRepository, times(1)).findRequestById(requestId);
    }

    @Test
    void testGetRequestById_NotFound() {
        when(requestRepository.findRequestById(requestId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            requestService.getRequestById(requestId);
        });

        assertEquals("Request not found", exception.getMessage());
        verify(requestRepository, times(1)).findRequestById(requestId);
    }

    @Test
    void testGetAllRequests() {
        Request request1 = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Product1")
                .setImageUrl("http://example.com/image1.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();
        Request request2 = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Product2")
                .setImageUrl("http://example.com/image2.jpg")
                .setPrice(200.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        when(requestRepository.getAllRequests()).thenReturn(Arrays.asList(request1, request2));

        List<Request> requests = requestService.getAllRequests();
        assertEquals(2, requests.size());
        assertEquals("Product1", requests.get(0).getProductName());
        assertEquals("Product2", requests.get(1).getProductName());
    }

    @Test
    void testUpdateRequest_Success() {
        Request updatedRequest = new Request.Builder()
                .setId(requestId)
                .setProductName("Updated Product Name")
                .setImageUrl("http://example.com/updated_image.jpg")
                .setPrice(150.0)
                .setStoreUrl("http://example.com/updated")
                .setCurrency("USD")
                .build();

        when(requestRepository.findRequestById(requestId)).thenReturn(Optional.of(request));
        when(requestRepository.save(any(Request.class))).thenReturn(updatedRequest);

        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 14000.0);
        rates.put("JPY", 130.0);
        rates.put("IDR", 1.0); // Assuming IDR has an exchange rate of 1.0 for simplicity
        apiResponse.put("rates", rates);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        Request result = requestService.updateRequest(requestId, updatedRequest);

        assertNotNull(result);
        assertEquals(updatedRequest.getPrice(), result.getPrice());
        assertEquals(updatedRequest.getImageUrl(), result.getImageUrl());
        verify(requestRepository, times(1)).findRequestById(requestId);
        verify(requestRepository, times(1)).save(updatedRequest);
    }

    @Test
    void testUpdateRequest_RequestNotFound() {
        UUID newRequestId = UUID.randomUUID();
        Request updatedRequest = new Request.Builder()
                .setId(requestId)
                .setProductName("Updated Product Name")
                .setImageUrl("http://example.com/updated_image.jpg")
                .setPrice(150.0)
                .setStoreUrl("http://example.com/updated")
                .setCurrency("USD")
                .build();

        when(requestRepository.findRequestById(newRequestId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            requestService.updateRequest(newRequestId, updatedRequest);
        });

        verify(requestRepository, times(1)).findRequestById(newRequestId);
        verifyNoMoreInteractions(requestRepository);
    }

    @Test
    void testDeleteRequest_Success() {
        doNothing().when(requestRepository).deleteRequestById(requestId);

        assertDoesNotThrow(() -> requestService.deleteRequest(requestId));

        verify(requestRepository, times(1)).deleteRequestById(requestId);
    }

    @Test
    void testDeleteRequest_NotFound() {
        UUID newRequestId = UUID.randomUUID();
        doThrow(new IllegalArgumentException("Request not found")).when(requestRepository).deleteRequestById(newRequestId);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            requestService.deleteRequest(newRequestId);
        });

        assertEquals("Request not found", exception.getMessage());
        verify(requestRepository, times(1)).deleteRequestById(newRequestId);
    }

    @Test
    void testGetExchangeRate_Success() {
        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 14000.0);
        apiResponse.put("rates", rates);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        double exchangeRate = requestService.getExchangeRate("USD");
        assertEquals(14000.0, exchangeRate);
    }

    @Test
    void testGetExchangeRate_UnsupportedCurrency() {
        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 14000.0);
        apiResponse.put("rates", rates);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            requestService.getExchangeRate("XXX");
        });
        assertEquals("Unsupported currency", exception.getMessage());
    }

    @Test
    void testConvertToIDR_USD() {
        double price = 10.0;
        double expectedIDR = 140000.0;
        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 14000.0);
        rates.put("JPY", 130.0);
        rates.put("IDR", 1.0); // Assuming IDR has an exchange rate of 1.0 for simplicity
        apiResponse.put("rates", rates);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        double actualIDR = requestService.convertToIDR(price, "USD");
        assertEquals(expectedIDR, actualIDR, 0.01); // allow for small floating-point errors
    }

    @Test
    void testConvertToIDR_JPY() {
        double price = 1000.0;
        double expectedIDR = 130000.0;
        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 14000.0);
        rates.put("JPY", 130.0);
        rates.put("IDR", 1.0); // Assuming IDR has an exchange rate of 1.0 for simplicity
        apiResponse.put("rates", rates);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        double actualIDR = requestService.convertToIDR(price, "JPY");
        assertEquals(expectedIDR, actualIDR, 0.01); // allow for small floating-point errors
    }

    @Test
    void testConvertToIDR_IDR() {
        double price = 15000.0;
        double expectedIDR = 15000.0;
        double actualIDR = requestService.convertToIDR(price, "IDR");
        assertEquals(expectedIDR, actualIDR);
    }

    @Test
    void testConvertToIDR_UnsupportedCurrency() {
        double price = 10.0;
        String unsupportedCurrency = "XXX";

        // Mocking external API call for exchange rates
        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 14000.0);
        rates.put("JPY", 130.0);
        apiResponse.put("rates", rates);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        // Ensure the exception is thrown
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            requestService.convertToIDR(price, unsupportedCurrency);
        });

        assertEquals("Unsupported currency", exception.getMessage());
    }

    @Test
    void testUpdateRequestStatus_Success() {
        when(requestRepository.findRequestById(requestId)).thenReturn(Optional.of(request));
        when(requestRepository.save(any(Request.class))).thenReturn(request);

        Request updatedRequest = requestService.updateRequestStatus(requestId, "approved");

        assertNotNull(updatedRequest);
        assertEquals("approved", updatedRequest.getStatus());
        verify(requestRepository, times(1)).findRequestById(requestId);
        verify(requestRepository, times(1)).save(request);
    }

    @Test
    void testUpdateRequestStatus_NotFound() {
        when(requestRepository.findRequestById(requestId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            requestService.updateRequestStatus(requestId, "approved");
        });

        assertEquals("Request not found", exception.getMessage());
        verify(requestRepository, times(1)).findRequestById(requestId);
        verifyNoMoreInteractions(requestRepository);
    }
}
