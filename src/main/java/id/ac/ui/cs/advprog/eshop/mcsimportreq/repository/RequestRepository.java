package id.ac.ui.cs.advprog.eshop.mcsimportreq.repository;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class RequestRepository {
    private List<Request> requests = new ArrayList<>();

    public Request save(Request request) {
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId().equals(request.getId())) {
                requests.set(i, request);
                return request;
            }
        }
        requests.add(request);
        return request;
    }

    public Request findRequestById(UUID requestId) {
        for (Request savedRequest : requests) {
            if (savedRequest.getId().equals(requestId)) {
                return savedRequest;
            }
        }
        return null;
    }

    public void deleteRequestById(UUID requestId) {
        requests.removeIf(request -> request.getId().equals(requestId));
    }

    public List<Request> getAllRequests() {
        return requests;
    }
}