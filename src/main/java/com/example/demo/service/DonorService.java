package com.example.demo.service;

import com.example.demo.model.Donor;
import com.example.demo.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonorService {
    
    private final DonorRepository donorRepository;
    
    @Transactional
    public Donor registerDonor(Donor donor) {
        return donorRepository.save(donor);
    }
    
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }
    
    public Optional<Donor> getDonorById(Long id) {
        return donorRepository.findById(id);
    }
    
    public Optional<Donor> getDonorByEmail(String email) {
        return donorRepository.findByEmail(email);
    }
    
    public List<Donor> searchDonors(String bloodGroup, String city) {
        if (bloodGroup != null && city != null) {
            return donorRepository.searchAvailableDonors(bloodGroup, city);
        } else if (bloodGroup != null) {
            return donorRepository.findByBloodGroup(bloodGroup);
        } else if (city != null) {
            return donorRepository.findByCity(city);
        }
        return donorRepository.findAll();
    }
    
    @Transactional
    public Donor updateDonor(Long id, Donor updatedDonor) {
        return donorRepository.findById(id)
                .map(donor -> {
                    donor.setName(updatedDonor.getName());
                    donor.setPhone(updatedDonor.getPhone());
                    donor.setAddress(updatedDonor.getAddress());
                    donor.setCity(updatedDonor.getCity());
                    donor.setState(updatedDonor.getState());
                    donor.setPincode(updatedDonor.getPincode());
                    donor.setAvailable(updatedDonor.getAvailable());
                    donor.setLastDonationDate(updatedDonor.getLastDonationDate());
                    return donorRepository.save(donor);
                })
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));
    }
    
    @Transactional
    public void toggleAvailability(Long id) {
        donorRepository.findById(id)
                .ifPresent(donor -> {
                    donor.setAvailable(!donor.getAvailable());
                    donorRepository.save(donor);
                });
    }
    
    @Transactional
    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }
    
    public List<Donor> getAvailableDonors() {
        return donorRepository.findByAvailable(true);
    }
    
    public Long getCountByBloodGroup(String bloodGroup) {
        return donorRepository.countByBloodGroup(bloodGroup);
    }
}
