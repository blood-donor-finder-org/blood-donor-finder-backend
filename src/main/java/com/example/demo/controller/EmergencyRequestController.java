package com.example.demo.controller;

import com.example.demo.model.EmergencyRequest;
import com.example.demo.service.EmergencyRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emergency-requests")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EmergencyRequestController {
    
    private final EmergencyRequestService requestService;
    
    @PostMapping
    public ResponseEntity<EmergencyRequest> createRequest(@Valid @RequestBody EmergencyRequest request) {
        EmergencyRequest savedRequest = requestService.createRequest(request);
        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<EmergencyRequest>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmergencyRequest> getRequestById(@PathVariable Long id) {
        return requestService.getRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/open")
    public ResponseEntity<List<EmergencyRequest>> getOpenRequests() {
        return ResponseEntity.ok(requestService.getOpenRequests());
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmergencyRequest>> getRequestsByStatus(@PathVariable String status) {
        try {
            EmergencyRequest.RequestStatus requestStatus = EmergencyRequest.RequestStatus.valueOf(status.toUpperCase());
            return ResponseEntity.ok(requestService.getRequestsByStatus(requestStatus));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<EmergencyRequest>> searchRequests(
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) String city) {
        return ResponseEntity.ok(requestService.searchRequests(bloodGroup, city));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EmergencyRequest> updateRequest(@PathVariable Long id, @Valid @RequestBody EmergencyRequest request) {
        try {
            EmergencyRequest updatedRequest = requestService.updateRequest(id, request);
            return ResponseEntity.ok(updatedRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<EmergencyRequest> updateRequestStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        try {
            EmergencyRequest.RequestStatus requestStatus = EmergencyRequest.RequestStatus.valueOf(status.toUpperCase());
            EmergencyRequest updatedRequest = requestService.updateRequestStatus(id, requestStatus);
            return ResponseEntity.ok(updatedRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}
