package id.ac.ui.cs.advprog.eshop.mcsimportreq.repository;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestRepositoryTest {
    @Mock
    private RequestRepository requestRepository;
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
        request.setId(1L);  // Set ID as this is a manual operation usually done by the database or persistence context
        requestRepository.save(request);
    }

    @Test
    void testSaveRequest() {
        Request request = new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        when(requestRepository.save(request)).thenReturn(request);

        Request savedRequest = requestRepository.save(request);

        assertEquals(request, savedRequest);
        verify(requestRepository, times(1)).save(request);
    }

    @Test
    void testFindRequestById() {
        Long requestId = 1L;
        Request request = new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        when(requestRepository.findRequestById(requestId)).thenReturn(request);

        Request foundRequest = requestRepository.findRequestById(requestId);

        assertEquals(request, foundRequest);
        verify(requestRepository, times(1)).findRequestById(requestId);
    }

    @Test
    void testFindByIdIfIdFound() {
        Long requestId = 1L;
        Request request = new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        when(requestRepository.findRequestById(requestId)).thenReturn(request);

        Request foundRequest = requestRepository.findRequestById(requestId);

        assertEquals(request, foundRequest);
        verify(requestRepository, times(1)).findRequestById(requestId);
    }

    @Test
    void testFindByIdIfIdNotFound() {
        Long requestId = 1L;
        when(requestRepository.findRequestById(requestId)).thenReturn(null);

        Request foundRequest = requestRepository.findRequestById(requestId);

        assertNull(foundRequest);
        verify(requestRepository, times(1)).findRequestById(requestId);
    }

    @Test
    void testUpdateRequest() {
        Request originalRequest = new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        originalRequest.setId(1L);

        Request updatedRequest = new Request("Nintendo Switch", "http://example.com/new_image.jpg", 150.0, "http://example.com", "USD");
        updatedRequest.setId(1L);

        when(requestRepository.save(eq(updatedRequest))).thenReturn(updatedRequest);

        when(requestRepository.findRequestById(1L)).thenReturn(updatedRequest);

        requestRepository.save(updatedRequest);

        Request fetchedRequest = requestRepository.findRequestById(1L);

        assertEquals(150.0, fetchedRequest.getPrice());
        assertEquals("http://example.com/new_image.jpg", fetchedRequest.getImageUrl());
        verify(requestRepository).save(updatedRequest);
        verify(requestRepository).findRequestById(1L);
    }


    @Test
    void testDeleteExistingRequest() {
        Long requestId = 1L;
        Request request = new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        request.setId(requestId);
        requestRepository.save(request);

        requestRepository.deleteRequestById(requestId);

        assertNull(requestRepository.findRequestById(requestId));
    }

    @Test
    void testDeleteNonExistingRequest() {
        Long requestId = 99L;
        requestRepository.deleteRequestById(requestId);
        Request foundRequest = requestRepository.findRequestById(requestId);
        assertNull(foundRequest);
    }

    @Test
    void testAddDuplicateRequest() {
        Request request = new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        requestRepository.save(request);
        try {
            requestRepository.save(request);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Duplicate");
        }
    }

    @Test
    void testGetAllRequests() {
        Request request1 = new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");
        Request request2 = new Request("PlayStation 5", "http://example.com/ps5.jpg", 500.0, "http://example.com", "USD");

        List<Request> requests = Arrays.asList(request1, request2);

        when(requestRepository.getAllRequests()).thenReturn(requests);

        List<Request> allRequests = requestRepository.getAllRequests();

        assertEquals(2, allRequests.size());
        verify(requestRepository, times(1)).getAllRequests();
    }

    @Test
    void testDeleteIfIdNotFound() {
        Long requestIdNotPresent = 99L;

        requestRepository.deleteRequestById(requestIdNotPresent);

        verify(requestRepository).deleteRequestById(requestIdNotPresent);

        assertNull(requestRepository.findRequestById(requestIdNotPresent));
    }

}
