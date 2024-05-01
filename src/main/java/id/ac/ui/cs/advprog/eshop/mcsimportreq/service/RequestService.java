package id.ac.ui.cs.advprog.eshop.mcsimportreq.service;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;

import java.util.List;

public interface RequestService {

    Request saveRequest(Request request);

    Request getRequestById(Long requestId);

    List<Request> getAllRequests();

    Request updateRequest(Long requestId, Request request);

    void deleteRequest(Long requestId);
}