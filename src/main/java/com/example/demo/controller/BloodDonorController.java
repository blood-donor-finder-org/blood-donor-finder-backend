package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.BloodDonor;
import com.example.demo.service.BloodDonorService;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin(origins = "http://localhost:3000")
public class BloodDonorController {
    
    private final BloodDonorService bloodDonorService;
    
    public BloodDonorController(BloodDonorService bloodDonorService) {
        this.bloodDonorService = bloodDonorService;
    }
    
    // Create - POST
    @PostMapping
    public ResponseEntity<BloodDonor> createDonor(@RequestBody BloodDonor donor) {
        BloodDonor createdDonor = bloodDonorService.createDonor(donor);
        return new ResponseEntity<>(createdDonor, HttpStatus.CREATED);
    }
    
    // Read - GET all donors
    @GetMapping
    public ResponseEntity<List<BloodDonor>> getAllDonors() {
        List<BloodDonor> donors = bloodDonorService.getAllDonors();
        return new ResponseEntity<>(donors, HttpStatus.OK);
    }
    
    // Read - GET donor by ID
    @GetMapping("/{id}")
    public ResponseEntity<BloodDonor> getDonorById(@PathVariable Long id) {
        return bloodDonorService.getDonorById(id)
                .map(donor -> new ResponseEntity<>(donor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    // Update - PUT
    @PutMapping("/{id}")
    public ResponseEntity<BloodDonor> updateDonor(@PathVariable Long id, @RequestBody BloodDonor donorDetails) {
        try {
            BloodDonor updatedDonor = bloodDonorService.updateDonor(id, donorDetails);
            return new ResponseEntity<>(updatedDonor, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
        try {
            bloodDonorService.deleteDonor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Search by blood group
    @GetMapping("/search/bloodgroup/{bloodGroup}")
    public ResponseEntity<List<BloodDonor>> findByBloodGroup(@PathVariable String bloodGroup) {
        List<BloodDonor> donors = bloodDonorService.findByBloodGroup(bloodGroup);
        return new ResponseEntity<>(donors, HttpStatus.OK);
    }
    
    // Search by city
    @GetMapping("/search/city/{city}")
    public ResponseEntity<List<BloodDonor>> findByCity(@PathVariable String city) {
        List<BloodDonor> donors = bloodDonorService.findByCity(city);
        return new ResponseEntity<>(donors, HttpStatus.OK);
    }
    
    // Search by blood group and city
    @GetMapping("/search")
    public ResponseEntity<List<BloodDonor>> findByBloodGroupAndCity(
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) String city) {
        
        if (bloodGroup != null && city != null) {
            List<BloodDonor> donors = bloodDonorService.findByBloodGroupAndCity(bloodGroup, city);
            return new ResponseEntity<>(donors, HttpStatus.OK);
        } else if (bloodGroup != null) {
            return findByBloodGroup(bloodGroup);
        } else if (city != null) {
            return findByCity(city);
        } else {
            return getAllDonors();
        }
    }
    
    // Get available donors
    @GetMapping("/available")
    public ResponseEntity<List<BloodDonor>> getAvailableDonors() {
        List<BloodDonor> donors = bloodDonorService.findAvailableDonors();
        return new ResponseEntity<>(donors, HttpStatus.OK);
    }
    
    // Get available donors by blood group
    @GetMapping("/available/{bloodGroup}")
    public ResponseEntity<List<BloodDonor>> getAvailableDonorsByBloodGroup(@PathVariable String bloodGroup) {
        List<BloodDonor> donors = bloodDonorService.findAvailableDonorsByBloodGroup(bloodGroup);
        return new ResponseEntity<>(donors, HttpStatus.OK);
    }
}
