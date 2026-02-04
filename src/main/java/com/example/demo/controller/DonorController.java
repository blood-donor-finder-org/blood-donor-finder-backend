package com.example.demo.controller;

import com.example.demo.model.Donor;
import com.example.demo.service.DonorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DonorController {
    
    private final DonorService donorService;
    
    @PostMapping
    public ResponseEntity<Donor> registerDonor(@Valid @RequestBody Donor donor) {
        Donor savedDonor = donorService.registerDonor(donor);
        return new ResponseEntity<>(savedDonor, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Donor>> getAllDonors() {
        return ResponseEntity.ok(donorService.getAllDonors());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Long id) {
        return donorService.getDonorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Donor> getDonorByEmail(@PathVariable String email) {
        return donorService.getDonorByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Donor>> searchDonors(
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) String city) {
        return ResponseEntity.ok(donorService.searchDonors(bloodGroup, city));
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<Donor>> getAvailableDonors() {
        return ResponseEntity.ok(donorService.getAvailableDonors());
    }
    
    @GetMapping("/count/{bloodGroup}")
    public ResponseEntity<Long> getCountByBloodGroup(@PathVariable String bloodGroup) {
        return ResponseEntity.ok(donorService.getCountByBloodGroup(bloodGroup));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Donor> updateDonor(@PathVariable Long id, @Valid @RequestBody Donor donor) {
        try {
            Donor updatedDonor = donorService.updateDonor(id, donor);
            return ResponseEntity.ok(updatedDonor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/toggle-availability")
    public ResponseEntity<Void> toggleAvailability(@PathVariable Long id) {
        donorService.toggleAvailability(id);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
        donorService.deleteDonor(id);
        return ResponseEntity.noContent().build();
    }
}
