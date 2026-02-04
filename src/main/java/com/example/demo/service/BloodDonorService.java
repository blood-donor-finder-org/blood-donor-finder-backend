package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.BloodDonor;
import com.example.demo.repository.BloodDonorRepository;

@Service
public class BloodDonorService {
    
    private final BloodDonorRepository bloodDonorRepository;
    
    public BloodDonorService(BloodDonorRepository bloodDonorRepository) {
        this.bloodDonorRepository = bloodDonorRepository;
    }
    
    // Create
    public BloodDonor createDonor(BloodDonor donor) {
        return bloodDonorRepository.save(donor);
    }
    
    // Read - Get all donors
    public List<BloodDonor> getAllDonors() {
        return bloodDonorRepository.findAll();
    }
    
    // Read - Get donor by ID
    public Optional<BloodDonor> getDonorById(Long id) {
        return bloodDonorRepository.findById(id);
    }
    
    // Update
    public BloodDonor updateDonor(Long id, BloodDonor donorDetails) {
        BloodDonor donor = bloodDonorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));
        
        donor.setName(donorDetails.getName());
        donor.setBloodGroup(donorDetails.getBloodGroup());
        donor.setEmail(donorDetails.getEmail());
        donor.setPhone(donorDetails.getPhone());
        donor.setAddress(donorDetails.getAddress());
        donor.setCity(donorDetails.getCity());
        donor.setState(donorDetails.getState());
        donor.setAge(donorDetails.getAge());
        donor.setAvailable(donorDetails.getAvailable());
        donor.setLastDonationDate(donorDetails.getLastDonationDate());
        
        return bloodDonorRepository.save(donor);
    }
    
    // Delete
    public void deleteDonor(Long id) {
        bloodDonorRepository.deleteById(id);
    }
    
    // Search by blood group
    public List<BloodDonor> findByBloodGroup(String bloodGroup) {
        return bloodDonorRepository.findByBloodGroup(bloodGroup);
    }
    
    // Search by city
    public List<BloodDonor> findByCity(String city) {
        return bloodDonorRepository.findByCity(city);
    }
    
    // Search by blood group and city
    public List<BloodDonor> findByBloodGroupAndCity(String bloodGroup, String city) {
        return bloodDonorRepository.findByBloodGroupAndCity(bloodGroup, city);
    }
    
    // Find available donors
    public List<BloodDonor> findAvailableDonors() {
        return bloodDonorRepository.findByAvailable(true);
    }
    
    // Find available donors by blood group
    public List<BloodDonor> findAvailableDonorsByBloodGroup(String bloodGroup) {
        return bloodDonorRepository.findByBloodGroupAndAvailable(bloodGroup, true);
    }
}
