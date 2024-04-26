package id.ac.ui.cs.advprog.eshop.mcsimportreq.repository;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
}
