package id.ac.ui.cs.advprog.eshop.mcsimportreq.service;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import id.ac.ui.cs.advprog.eshop.mcsimportreq.repository.RequestRepository;
import id.ac.ui.cs.advprog.eshop.mcsimportreq.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public Request saveRequest(Request request) {
        // Logika validasi atau transformasi bisa di sini
        return requestRepository.save(request);
    }

    @Override
    public Request getRequestById(Long requestId) {
        return requestRepository.findById(requestId).orElse(null);
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public Request updateRequest(Long requestId, Request updatedRequest) {
        return requestRepository.findById(requestId).map(existingRequest -> {
            existingRequest.setProductName(updatedRequest.getProductName());
            existingRequest.setImageUrl(updatedRequest.getImageUrl());
            existingRequest.setPrice(updatedRequest.getPrice());
            existingRequest.setStoreUrl(updatedRequest.getStoreUrl());
            existingRequest.setCurrency(updatedRequest.getCurrency());
            return requestRepository.save(existingRequest);
        }).orElseThrow(() -> new IllegalArgumentException("Request not found with ID: " + requestId));
    }

    @Override
    public void deleteRequest(Long requestId) {
        requestRepository.deleteById(requestId);
    }
}
