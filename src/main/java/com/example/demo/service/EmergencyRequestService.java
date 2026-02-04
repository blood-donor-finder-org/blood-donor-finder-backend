package com.example.demo.service;

import com.example.demo.model.EmergencyRequest;
import com.example.demo.repository.EmergencyRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmergencyRequestService {
    
    private final EmergencyRequestRepository requestRepository;
    
    @Transactional
    public EmergencyRequest createRequest(EmergencyRequest request) {
        request.setStatus(EmergencyRequest.RequestStatus.OPEN);
        return requestRepository.save(request);
    }
    
    public List<EmergencyRequest> getAllRequests() {
        return requestRepository.findAll();
    }
    
    public Optional<EmergencyRequest> getRequestById(Long id) {
        return requestRepository.findById(id);
    }
    
    public List<EmergencyRequest> getOpenRequests() {
        return requestRepository.findAllOpenRequestsOrderedByUrgency();
    }
    
    public List<EmergencyRequest> getRequestsByStatus(EmergencyRequest.RequestStatus status) {
        return requestRepository.findByStatusOrderByCreatedAtDesc(status);
    }
    
    public List<EmergencyRequest> searchRequests(String bloodGroup, String city) {
        if (bloodGroup != null && city != null) {
            return requestRepository.findByBloodGroupAndCity(bloodGroup, city);
        } else if (bloodGroup != null) {
            return requestRepository.findByBloodGroup(bloodGroup);
        } else if (city != null) {
            return requestRepository.findByCity(city);
        }
        return requestRepository.findAll();
    }
    
    @Transactional
    public EmergencyRequest updateRequestStatus(Long id, EmergencyRequest.RequestStatus status) {
        return requestRepository.findById(id)
                .map(request -> {
                    request.setStatus(status);
                    return requestRepository.save(request);
                })
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id));
    }
    
    @Transactional
    public EmergencyRequest updateRequest(Long id, EmergencyRequest updatedRequest) {
        return requestRepository.findById(id)
                .map(request -> {
                    request.setPatientName(updatedRequest.getPatientName());
                    request.setUnitsRequired(updatedRequest.getUnitsRequired());
                    request.setHospitalName(updatedRequest.getHospitalName());
                    request.setHospitalAddress(updatedRequest.getHospitalAddress());
                    request.setContactPerson(updatedRequest.getContactPerson());
                    request.setContactNumber(updatedRequest.getContactNumber());
                    request.setRequiredBy(updatedRequest.getRequiredBy());
                    request.setAdditionalInfo(updatedRequest.getAdditionalInfo());
                    request.setStatus(updatedRequest.getStatus());
                    return requestRepository.save(request);
                })
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id));
    }
    
    @Transactional
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }
}
