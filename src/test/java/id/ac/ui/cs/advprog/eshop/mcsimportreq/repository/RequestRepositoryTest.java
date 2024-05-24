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
        request = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();
        requestRepository.save(request);
    }

    @Test
    void testSaveRequest() {
        Request newRequest = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("PlayStation 5")
                .setImageUrl("http://example.com/ps5.jpg")
                .setPrice(500.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        Request savedRequest = requestRepository.save(newRequest);

        assertEquals(newRequest, savedRequest);
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
        Request updatedRequest = new Request.Builder()
                .setId(request.getId())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/new_image.jpg")
                .setPrice(150.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

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
        Request request1 = new Request.Builder()
                .setId(UUID.randomUUID())
                .setProductName("PlayStation 5")
                .setImageUrl("http://example.com/ps5.jpg")
                .setPrice(500.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();
        requestRepository.save(request1);

        List<Request> allRequests = requestRepository.getAllRequests();

        assertEquals(2, allRequests.size());
        assertThat(allRequests).containsExactlyInAnyOrder(request, request1);
    }

    @Test
    void testAddDuplicateRequest() {
        Request duplicateRequest = new Request.Builder()
                .setId(request.getId())
                .setProductName("Nintendo Switch")
                .setImageUrl("http://example.com/image.jpg")
                .setPrice(100.0)
                .setStoreUrl("http://example.com")
                .setCurrency("USD")
                .build();

        requestRepository.save(duplicateRequest);

        assertEquals(1, requestRepository.getAllRequests().size());
        Request foundRequest = requestRepository.findRequestById(request.getId());
        assertEquals(duplicateRequest, foundRequest);
    }
}
