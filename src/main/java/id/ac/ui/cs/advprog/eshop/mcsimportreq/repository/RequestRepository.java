package id.ac.ui.cs.advprog.eshop.mcsimportreq.repository;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RequestRepository {
    private List<Request> requests = new ArrayList<>();

    public Request save(Request request) {
        // If request doesn't have an ID, generate a new one
        if (request.getId() == null) {
            request.setId(UUID.randomUUID());
        }

        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId().equals(request.getId())) {
                requests.set(i, request);
                return request;
            }
        }
        requests.add(request);
        return request;
    }

    public Optional<Request> findRequestById(UUID requestId) {
        return requests.stream()
                .filter(request -> request.getId().equals(requestId))
                .findFirst();
    }

    public void deleteRequestById(UUID requestId) {
        requests.removeIf(request -> request.getId().equals(requestId));
    }

    public List<Request> getAllRequests() {
        return requests;
    }
}