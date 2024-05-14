package id.ac.ui.cs.advprog.eshop.mcsimportreq.repository;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class RequestRepositoryTest {

    @Autowired
    private RequestRepository requestRepository;

@Test
public void testSaveAndRetrieveRequest() {
    Request request = new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD");

    Request savedRequest = requestRepository.save(request);
    assertThat(savedRequest).isNotNull();
    assertThat(savedRequest.getId()).isNotNull();
    assertThat(savedRequest.getProductName()).isEqualTo("Nintendo Switch");

    Optional<Request> foundRequestOptional = requestRepository.findById(savedRequest.getId());

    assertThat(foundRequestOptional).isPresent();
    foundRequestOptional.ifPresent(foundRequest -> {
        assertThat(foundRequest.getProductName()).isEqualTo("Nintendo Switch");
        assertThat(foundRequest.getImageUrl()).isEqualTo("http://example.com/image.jpg");
        assertThat(foundRequest.getPrice()).isEqualTo(100.0);
        assertThat(foundRequest.getStoreUrl()).isEqualTo("http://example.com");
        assertThat(foundRequest.getCurrency()).isEqualTo("USD");
    });
}

    @Test
    public void testUpdateRequest() {
        Request request = new Request("PlayStation 5", "http://example.com/ps5.jpg", 500.0, "http://example.com", "USD");
        Request savedRequest = requestRepository.save(request);
        savedRequest.setPrice(550.0);
        Request updatedRequest = requestRepository.save(savedRequest);

        assertThat(updatedRequest.getPrice()).isEqualTo(550.0);
    }

    @Test
    public void testDeleteRequest() {
        Request request = new Request("Xbox Series X", "http://example.com/xbox.jpg", 450.0, "http://example.com", "USD");
        Request savedRequest = requestRepository.save(request);
        assertThat(requestRepository.existsById(savedRequest.getId())).isTrue();

        requestRepository.deleteById(savedRequest.getId());
        assertThat(requestRepository.existsById(savedRequest.getId())).isFalse();
    }

    @Test
    public void testFindAllRequests() {
        requestRepository.save(new Request("Nintendo Switch", "http://example.com/image.jpg", 100.0, "http://example.com", "USD"));
        requestRepository.save(new Request("PlayStation 5", "http://example.com/ps5.jpg", 500.0, "http://example.com", "USD"));

        List<Request> requests = requestRepository.findAll();
        assertThat(requests).hasSize(2);
    }
}
