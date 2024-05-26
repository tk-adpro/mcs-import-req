package id.ac.ui.cs.advprog.eshop.mcsimportreq.service;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import id.ac.ui.cs.advprog.eshop.mcsimportreq.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/IDR";
    private static final String REQUEST_NOT_FOUND_MESSAGE = "Request not found";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Request saveRequest(Request request) {
        validateCurrency(request.getCurrency());

        double convertedPrice = convertToIDR(request.getPrice(), request.getCurrency());

        request.setPrice(convertedPrice);
        request.setStatus("waiting"); // Set default status to 'waiting'

        return requestRepository.save(request);
    }

    @Override
    public Request getRequestById(UUID requestId) {
        return requestRepository.findRequestById(requestId).orElseThrow(() -> new IllegalArgumentException(REQUEST_NOT_FOUND_MESSAGE));
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.getAllRequests();
    }

    @Override
    public Request updateRequest(UUID requestId, Request updatedRequest) {
        Request existingRequest = requestRepository.findRequestById(requestId).orElseThrow(() -> new IllegalArgumentException(REQUEST_NOT_FOUND_MESSAGE));
        validateCurrency(updatedRequest.getCurrency());
        double convertedPrice = convertToIDR(updatedRequest.getPrice(), updatedRequest.getCurrency());
        updatedRequest.setPrice(convertedPrice);
        updatedRequest.setId(requestId);
        updatedRequest.setStatus(existingRequest.getStatus()); // Preserve the existing status
        return requestRepository.save(updatedRequest);
    }

    @Override
    public void deleteRequest(UUID requestId) {
        requestRepository.deleteRequestById(requestId);
    }

    @Override
    public double getExchangeRate(String currencyCode) {
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);

        Map<String, Double> rates = (Map<String, Double>) response.get("rates");
        if (rates.containsKey(currencyCode)) {
            double plainRate = rates.get(currencyCode);
            BigDecimal scientificNumber = BigDecimal.valueOf(plainRate);

            // Convert it to plain string representation
            String plainString = scientificNumber.toPlainString();
            return Double.valueOf(plainString);
        } else {
            throw new IllegalArgumentException("Unsupported currency");
        }
    }

    public double convertToIDR(double price, String currencyCode) {
        if (currencyCode.equalsIgnoreCase("IDR")) {
            return price;
        } else {
            double rate = getExchangeRate(currencyCode);
            return price * rate;
        }
    }

    @Override
    public Request updateRequestStatus(UUID requestId, String status) {
        Request request = requestRepository.findRequestById(requestId).orElseThrow(() -> new IllegalArgumentException(REQUEST_NOT_FOUND_MESSAGE));
        request.setStatus(status);
        return requestRepository.save(request);
    }

    private void validateCurrency(String currencyCode) {
        if (currencyCode == null || currencyCode.isEmpty()) {
            throw new IllegalArgumentException("Currency code cannot be null or empty");
        }
        try {
            Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency code: " + currencyCode);
        }
    }
}