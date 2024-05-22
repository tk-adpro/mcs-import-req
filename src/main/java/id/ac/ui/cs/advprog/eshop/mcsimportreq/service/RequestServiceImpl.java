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

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/IDR";

    @Autowired
    private RestTemplate restTemplate;

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
            Currency currency = Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency code");
        }
    }

    public double getExchangeRate(String currencyCode) {
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);

        Map<String, Double> rates = (Map<String, Double>) response.get("rates");
        if (rates.containsKey(currencyCode)) {
            double plainRate = rates.get(currencyCode);
            BigDecimal scientificNumber = new BigDecimal(plainRate);

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
}