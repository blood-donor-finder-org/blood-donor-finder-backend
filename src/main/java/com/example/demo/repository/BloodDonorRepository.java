package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BloodDonor;

@Repository
public interface BloodDonorRepository extends JpaRepository<BloodDonor, Long> {
    
    // Custom query methods
    List<BloodDonor> findByBloodGroup(String bloodGroup);
    
    List<BloodDonor> findByCity(String city);
    
    List<BloodDonor> findByBloodGroupAndCity(String bloodGroup, String city);
    
    List<BloodDonor> findByAvailable(Boolean available);
    
    List<BloodDonor> findByBloodGroupAndAvailable(String bloodGroup, Boolean available);
}
