package id.ac.ui.cs.advprog.eshop.mcsimportreq.controller;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import id.ac.ui.cs.advprog.eshop.mcsimportreq.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api-requests")
public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/public/{requestId}")
    public ResponseEntity<Request> getRequestById(@PathVariable UUID requestId) {
        try {
            Request request = requestService.getRequestById(requestId);
            return new ResponseEntity<>(request, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching request by ID", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/public/create")
//    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
//        try {
//            Request savedRequest = requestService.saveRequest(request);
//            return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
//        } catch (Exception e) {
//            logger.error("Error creating request", e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/public/create")
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        try {
            logger.info("Creating request: {}", request);
            Request savedRequest = requestService.saveRequest(request);
            logger.info("Request created successfully: {}", savedRequest);
            return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/public/edit/{requestId}")
    public ResponseEntity<Request> updateRequest(@PathVariable UUID requestId, @RequestBody Request updatedRequest) {
        try {
            Request result = requestService.updateRequest(requestId, updatedRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating request", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/public/delete/{requestId}")
    public ResponseEntity<Void> deleteRequest(@PathVariable UUID requestId) {
        try {
            requestService.deleteRequest(requestId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting request", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/public/all")
    public ResponseEntity<List<Request>> getAllRequests() {
        try {
            List<Request> requests = requestService.getAllRequests();
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all requests", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admin/status/{requestId}")
    public ResponseEntity<Request> updateRequestStatus(@PathVariable UUID requestId, @RequestBody Map<String, String> statusUpdate) {
        try {
            String status = statusUpdate.get("status");
            Request updatedRequest = requestService.updateRequestStatus(requestId, status);
            return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating request status", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}