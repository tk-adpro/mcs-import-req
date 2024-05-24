package id.ac.ui.cs.advprog.eshop.mcsimportreq.repository;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.assertj.core.api.Assertions.assertThat;

public class RequestRepositoryTest {

    private RequestRepository requestRepository;
    private Request request;

    @BeforeEach
    void setUp() {
        requestRepository = new RequestRepository();
        request = new Request(
                UUID.randomUUID(),
                "Nintendo Switch",
                "http://example.com/image.jpg",
                100.0,
                "http://example.com",
                "USD"
        );
        requestRepository.save(request);
    }

    @Test
    void testSaveRequest() {
        Request request = new Request(UUID.randomUUID(), "PlayStation 5", "http://example.com/ps5.jpg", 500.0, "http://example.com", "USD");

        Request savedRequest = requestRepository.save(request);

        assertEquals(request, savedRequest);
        assertEquals(2, requestRepository.getAllRequests().size());
    }

    @Test
    void testFindRequestById() {
        UUID requestId = request.getId();
        Request foundRequest = requestRepository.findRequestById(requestId);

        assertEquals(request, foundRequest);
    }

    @Test
    void testFindByIdIfIdFound() {
        UUID requestId = request.getId();
        Request foundRequest = requestRepository.findRequestById(requestId);

        assertEquals(request, foundRequest);
    }

    @Test
    void testFindByIdIfIdNotFound() {
        UUID requestId = UUID.randomUUID();
        Request foundRequest = requestRepository.findRequestById(requestId);

        assertNull(foundRequest);
    }

    @Test
    void testUpdateRequest() {
        Request updatedRequest = new Request(request.getId(), "Nintendo Switch", "http://example.com/new_image.jpg", 150.0, "http://example.com", "USD");

        requestRepository.save(updatedRequest);

        Request fetchedRequest = requestRepository.findRequestById(request.getId());

        assertEquals(150.0, fetchedRequest.getPrice());
        assertEquals("http://example.com/new_image.jpg", fetchedRequest.getImageUrl());
        assertEquals(1, requestRepository.getAllRequests().size());
    }

    @Test
    void testDeleteExistingRequest() {
        UUID requestId = request.getId();
        requestRepository.deleteRequestById(requestId);

        assertNull(requestRepository.findRequestById(requestId));
        assertEquals(0, requestRepository.getAllRequests().size());
    }

    @Test
    void testDeleteNonExistingRequest() {
        UUID requestId = UUID.randomUUID();
        requestRepository.deleteRequestById(requestId);
        Request foundRequest = requestRepository.findRequestById(requestId);

        assertNull(foundRequest);
        assertEquals(1, requestRepository.getAllRequests().size());
    }

    @Test
    void testGetAllRequests() {
        Request request1 = new Request(UUID.randomUUID(), "PlayStation 5", "http://example.com/ps5.jpg", 500.0, "http://example.com", "USD");
        requestRepository.save(request1);

        List<Request> allRequests = requestRepository.getAllRequests();

        assertEquals(2, allRequests.size());
        assertThat(allRequests).containsExactlyInAnyOrder(request, request1);
    }

    @Test
    void testAddDuplicateRequest() {
        Request duplicateRequest = new Request(request.getId(), "Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");

        requestRepository.save(duplicateRequest);

        assertEquals(1, requestRepository.getAllRequests().size());
        Request foundRequest = requestRepository.findRequestById(request.getId());
        assertEquals(duplicateRequest, foundRequest);
    }
}
