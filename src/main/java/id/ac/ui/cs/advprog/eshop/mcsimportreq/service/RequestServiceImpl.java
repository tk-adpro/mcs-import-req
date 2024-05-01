package id.ac.ui.cs.advprog.eshop.mcsimportreq.service;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import id.ac.ui.cs.advprog.eshop.mcsimportreq.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request saveRequest(Request request) {
        validateCurrency(request.getCurrency());

        double convertedPrice = convertToIDR(request.getPrice(), request.getCurrency());

        request.setPrice(convertedPrice);

        return requestRepository.save(request);
    }

    @Override
    public Request getRequestById(Long requestId) {
        return requestRepository.findRequestById(requestId);
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.getAllRequests();
    }

    @Override
    public Request updateRequest(Long requestId, Request updatedRequest) {
        Request existingRequest = requestRepository.findRequestById(requestId);
        if (existingRequest == null) {
            throw new IllegalArgumentException("Request not found");
        }
        validateCurrency(updatedRequest.getCurrency());
        double convertedPrice = convertToIDR(updatedRequest.getPrice(), updatedRequest.getCurrency());
        updatedRequest.setPrice(convertedPrice);
        updatedRequest.setId(requestId);
        return requestRepository.save(updatedRequest);
    }

    @Override
    public void deleteRequest(Long requestId) {
        requestRepository.deleteRequestById(requestId);
    }

    private void validateCurrency(String currencyCode) {
        try {
            Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency code");
        }
    }

    private double convertToIDR(double price, String currencyCode) {

        // Asumsikan kurs IDR/USD = 14000
        if (currencyCode.equalsIgnoreCase("USD")) {
            return price * 14000;
        }
        // Asumsikan kurs IDR/JPY = 130
        else if (currencyCode.equalsIgnoreCase("JPY")) {
            return price * 130;
        }
        else if (currencyCode.equalsIgnoreCase("IDR")) {
            return price;
        } else {
            throw new IllegalArgumentException("Unsupported currency");
        }
    }
}