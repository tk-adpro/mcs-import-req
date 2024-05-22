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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @BeforeEach
    void setUp() {
        request = new Request(
                "Nintendo Switch",
                "http://example.com/image.jpg",
                100.0,
                "http://example.com",
                "USD"
        );
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
    void testGetRequestById_Success() {
        Long requestId = 1L;
        when(requestRepository.findRequestById(requestId)).thenReturn(request);

        Request foundRequest = requestService.getRequestById(requestId);

        assertNotNull(foundRequest);
        assertEquals(request, foundRequest);
        verify(requestRepository, times(1)).findRequestById(requestId);
    }

    @Test
    void testUpdateRequest_Success() {
        Long requestId = 1L;
        Request updatedRequest = new Request(
                "Updated Product Name",
                "http://example.com/updated_image.jpg",
                150.0,
                "http://example.com/updated",
                "USD"
        );
        when(requestRepository.findRequestById(requestId)).thenReturn(request);
        when(requestRepository.save(updatedRequest)).thenReturn(updatedRequest);
        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 14000.0);
        rates.put("JPY", 130.0);
        rates.put("IDR", 1.0); // Assuming IDR has an exchange rate of 1.0 for simplicity
        apiResponse.put("rates", rates);
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        Request result = requestService.updateRequest(requestId, updatedRequest);

        assertNotNull(result);
        assertEquals(updatedRequest, result);
        verify(requestRepository, times(1)).findRequestById(requestId);
        verify(requestRepository, times(1)).save(updatedRequest);
    }

    @Test
    void testUpdateRequest_RequestNotFound() {
        Long requestId = 1L;
        Request updatedRequest = new Request(
                "Updated Product Name",
                "http://example.com/updated_image.jpg",
                150.0,
                "http://example.com/updated",
                "USD"
        );

        when(requestRepository.findRequestById(requestId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            requestService.updateRequest(requestId, updatedRequest);
        });

        verify(requestRepository, times(1)).findRequestById(requestId);
        verifyNoMoreInteractions(requestRepository);
    }

    @Test
    void testSaveRequest_InvalidCurrency() {
        request.setCurrency("XYZ"); // Currency code yang tidak valid

        assertThrows(IllegalArgumentException.class, () -> {
            requestService.saveRequest(request);
        });

        verifyNoInteractions(requestRepository);
    }

    @Test
    void testDeleteRequest_Success() {
        Long requestId = 1L;
        doNothing().when(requestRepository).deleteRequestById(requestId);

        assertDoesNotThrow(() -> requestService.deleteRequest(requestId));

        verify(requestRepository, times(1)).deleteRequestById(requestId);
    }

    @Test
    void testDeleteRequest_Failure() {
        Long requestId = 1L;
        doThrow(new IllegalArgumentException("Request not found")).when(requestRepository).deleteRequestById(requestId);

        assertThrows(IllegalArgumentException.class, () -> requestService.deleteRequest(requestId));

        verify(requestRepository, times(1)).deleteRequestById(requestId);
    }
    @Test
    public void testConvertToIDR_USD() {
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
    public void testConvertToIDR_JPY() {
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
    public void testConvertToIDR_IDR() {
        double price = 15000.0;
        double expectedIDR = 15000.0;
        double actualIDR = requestService.convertToIDR(price, "IDR");
        assertEquals(expectedIDR, actualIDR);
    }

    @Test
    public void testConvertToIDR_UnsupportedCurrency() {
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
    void testGetAllRequests() {
        Request request1 = new Request("Product1", "http://example.com/image1.jpg", 100.0, "http://example.com", "USD");
        Request request2 = new Request("Product2", "http://example.com/image2.jpg", 200.0, "http://example.com", "USD");

        when(requestRepository.getAllRequests()).thenReturn(Arrays.asList(request1, request2));

        List<Request> requests = requestService.getAllRequests();
        assertEquals(2, requests.size());
        assertEquals("Product1", requests.get(0).getProductName());
        assertEquals("Product2", requests.get(1).getProductName());
    }
}