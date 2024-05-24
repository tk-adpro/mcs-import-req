package id.ac.ui.cs.advprog.eshop.mcsimportreq.service;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;

import java.util.List;
import java.util.UUID;

public interface RequestService {

    Request saveRequest(Request request);

    Request getRequestById(UUID requestId);

    List<Request> getAllRequests();

    Request updateRequest(UUID requestId, Request request);

    void deleteRequest(UUID requestId);
    double getExchangeRate(String currencyCode);
}