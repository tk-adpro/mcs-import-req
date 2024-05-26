package id.ac.ui.cs.advprog.eshop.mcsimportreq.controller;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import id.ac.ui.cs.advprog.eshop.mcsimportreq.service.RequestService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestControllerTest {

    @Mock
    private RequestService requestService;

    private RequestController requestController;

    @BeforeEach
    void setUp() {
        requestController = new RequestController(requestService);
    }

    @Test
    void getRequestById_Exists() {
        UUID requestId = UUID.randomUUID();
        Request request = new Request.Builder()
                .setId(requestId)
                .setProductName("Product Name")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();
        when(requestService.getRequestById(requestId)).thenReturn(request);

        ResponseEntity<Request> response = requestController.getRequestById(requestId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request, response.getBody());
    }

    @Test
    void getRequestById_NotFound() {
        UUID requestId = UUID.randomUUID();
        when(requestService.getRequestById(requestId)).thenReturn(null);

        ResponseEntity<Request> response = requestController.getRequestById(requestId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createRequest() {
        UUID requestId = UUID.randomUUID();
        Request request = new Request.Builder()
                .setId(requestId)
                .setProductName("Product Name")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();
        when(requestService.saveRequest(request)).thenReturn(request);

        ResponseEntity<Request> response = requestController.createRequest(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(request, response.getBody());
    }

    @Test
    void updateRequest_Success() {
        UUID requestId = UUID.randomUUID();
        Request request = new Request.Builder()
                .setId(requestId)
                .setProductName("Product Name")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();
        when(requestService.updateRequest(requestId, request)).thenReturn(request);

        ResponseEntity<Request> response = requestController.updateRequest(requestId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request, response.getBody());
    }

    @Test
    void updateRequest_NotFound() {
        UUID requestId = UUID.randomUUID();
        Request request = new Request.Builder()
                .setId(requestId)
                .setProductName("Product Name")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();
        when(requestService.updateRequest(requestId, request)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Request> response = requestController.updateRequest(requestId, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteRequest_Success() {
        UUID requestId = UUID.randomUUID();
        doNothing().when(requestService).deleteRequest(requestId);

        ResponseEntity<Void> response = requestController.deleteRequest(requestId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(requestService, times(1)).deleteRequest(requestId);
    }

    @Test
    void deleteRequest_NotFound() {
        UUID requestId = UUID.randomUUID();
        doThrow(new IllegalArgumentException()).when(requestService).deleteRequest(requestId);

        ResponseEntity<Void> response = requestController.deleteRequest(requestId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(requestService, times(1)).deleteRequest(requestId);
    }

    @Test
    void getAllRequests() {
        List<Request> requests = Arrays.asList(
                new Request.Builder()
                        .setId(UUID.randomUUID())
                        .setProductName("Product Name 1")
                        .setImageUrl("http://example.com/image1.jpg")
                        .setPrice(100.0)
                        .setStoreUrl("http://example.com/1")
                        .setCurrency("USD")
                        .build(),
                new Request.Builder()
                        .setId(UUID.randomUUID())
                        .setProductName("Product Name 2")
                        .setImageUrl("http://example.com/image2.jpg")
                        .setPrice(200.0)
                        .setStoreUrl("http://example.com/2")
                        .setCurrency("USD")
                        .build()
        );
        when(requestService.getAllRequests()).thenReturn(requests);

        ResponseEntity<List<Request>> response = requestController.getAllRequests();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requests, response.getBody());
    }

    @Test
    void updateRequestStatus_Success() {
        UUID requestId = UUID.randomUUID();
        Request request = new Request.Builder()
                .setId(requestId)
                .setProductName("Product Name")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .setStatus("waiting")
                .build();
        when(requestService.updateRequestStatus(requestId, "approved")).thenReturn(request);

        ResponseEntity<Request> response = requestController.updateRequestStatus(requestId, Map.of("status", "approved"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request, response.getBody());
    }

    @Test
    void updateRequestStatus_NotFound() {
        UUID requestId = UUID.randomUUID();
        when(requestService.updateRequestStatus(requestId, "approved")).thenThrow(new IllegalArgumentException("Request not found"));

        ResponseEntity<Request> response = requestController.updateRequestStatus(requestId, Map.of("status", "approved"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}