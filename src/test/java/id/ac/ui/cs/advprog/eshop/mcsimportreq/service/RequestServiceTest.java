package id.ac.ui.cs.advprog.eshop.mcsimportreq.service;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import id.ac.ui.cs.advprog.eshop.mcsimportreq.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;

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

        Request savedRequest = requestService.saveRequest(request);

        assertNotNull(savedRequest);
        assertEquals(request, savedRequest);
        verify(requestRepository, times(1)).save(request);
    }

    @Test
    void testGetRequestById_Success() {
        Long requestId = 1L;
        when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));

        Request foundRequest = requestService.getRequestById(requestId);

        assertNotNull(foundRequest);
        assertEquals(request, foundRequest);
        verify(requestRepository, times(1)).findById(requestId);
    }

    @Test
    void testUpdateRequestById_Success() {
        Long requestId = 1L;
        when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));

        Request foundRequest = requestService.getRequestById(requestId);

        assertNotNull(foundRequest);
        assertEquals(request, foundRequest);
        verify(requestRepository, times(1)).findById(requestId);
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
        when(requestRepository.findById(requestId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            requestService.updateRequest(requestId, updatedRequest);
        });

        verify(requestRepository, times(1)).findById(requestId);
    }

    @Test
    void testDeleteRequest_Success() {
        Long requestId = 1L;
        doNothing().when(requestRepository).deleteById(requestId);

        assertDoesNotThrow(() -> requestService.deleteRequest(requestId));

        verify(requestRepository, times(1)).deleteById(requestId);
    }

    @Test
    void testDeleteRequest_Failure() {
        Long requestId = 1L;
        doThrow(new IllegalArgumentException("Request not found")).when(requestRepository).deleteById(requestId);

        assertThrows(IllegalArgumentException.class, () -> requestService.deleteRequest(requestId));

        verify(requestRepository, times(1)).deleteById(requestId);
    }

}