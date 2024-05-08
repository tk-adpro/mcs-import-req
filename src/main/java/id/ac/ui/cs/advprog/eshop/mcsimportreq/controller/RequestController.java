package id.ac.ui.cs.advprog.eshop.mcsimportreq.controller;
import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import id.ac.ui.cs.advprog.eshop.mcsimportreq.service.RequestService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long requestId) {
        Request request = requestService.getRequestById(requestId);
        if (request != null) {
            return new ResponseEntity<>(request, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        Request savedRequest = requestService.saveRequest(request);
        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{requestId}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long requestId, @RequestBody Request updatedRequest) {
        try {
            Request result = requestService.updateRequest(requestId, updatedRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long requestId) {
        try {
            requestService.deleteRequest(requestId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}