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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

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
        Long requestId = 1L;
        Request request = new Request(
                "Product Name",
                "http://example.com/image.jpg",
                100.0,
                "http://example.com",
                "USD"
        );
        when(requestService.getRequestById(requestId)).thenReturn(request);

        ResponseEntity<Request> response = requestController.getRequestById(requestId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request, response.getBody());
    }

    @Test
    void getRequestById_NotFound() {
        Long requestId = 1L;
        when(requestService.getRequestById(requestId)).thenReturn(null);

        ResponseEntity<Request> response = requestController.getRequestById(requestId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createRequest() {
        Request request = new Request(
                "Product Name",
                "http://example.com/image.jpg",
                100.0,
                "http://example.com",
                "USD"
        );
        when(requestService.saveRequest(request)).thenReturn(request);

        ResponseEntity<Request> response = requestController.createRequest(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(request, response.getBody());
    }

    @Test
    void updateRequest_Success() {
        Long requestId = 1L;
        Request request = new Request(
                "Product Name",
                "http://example.com/image.jpg",
                100.0,
                "http://example.com",
                "USD"
        );
        when(requestService.updateRequest(requestId, request)).thenReturn(request);

        ResponseEntity<Request> response = requestController.updateRequest(requestId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request, response.getBody());
    }

    @Test
    void updateRequest_NotFound() {
        Long requestId = 1L;
        Request request = new Request(
                "Product Name",
                "http://example.com/image.jpg",
                100.0,
                "http://example.com",
                "USD"
        );
        when(requestService.updateRequest(requestId, request)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Request> response = requestController.updateRequest(requestId, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteRequest_Success() {
        Long requestId = 1L;
        doNothing().when(requestService).deleteRequest(requestId);

        ResponseEntity<Void> response = requestController.deleteRequest(requestId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(requestService, times(1)).deleteRequest(requestId);
    }

    @Test
    void deleteRequest_NotFound() {
        Long requestId = 1L;
        doThrow(new IllegalArgumentException()).when(requestService).deleteRequest(requestId);

        ResponseEntity<Void> response = requestController.deleteRequest(requestId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(requestService, times(1)).deleteRequest(requestId);
    }
}

