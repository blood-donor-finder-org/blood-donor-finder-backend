package com.example.demo.repository;

import com.example.demo.model.EmergencyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyRequestRepository extends JpaRepository<EmergencyRequest, Long> {
    
    List<EmergencyRequest> findByStatus(EmergencyRequest.RequestStatus status);
    
    List<EmergencyRequest> findByBloodGroup(String bloodGroup);
    
    List<EmergencyRequest> findByCity(String city);
    
    List<EmergencyRequest> findByBloodGroupAndCity(String bloodGroup, String city);
    
    @Query("SELECT e FROM EmergencyRequest e WHERE e.status = 'OPEN' ORDER BY e.requiredBy ASC")
    List<EmergencyRequest> findAllOpenRequestsOrderedByUrgency();
    
    List<EmergencyRequest> findByStatusOrderByCreatedAtDesc(EmergencyRequest.RequestStatus status);
}
