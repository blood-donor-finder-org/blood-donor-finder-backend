package com.example.demo.repository;

import com.example.demo.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    
    Optional<Donor> findByEmail(String email);
    
    List<Donor> findByBloodGroup(String bloodGroup);
    
    List<Donor> findByCity(String city);
    
    List<Donor> findByBloodGroupAndCity(String bloodGroup, String city);
    
    List<Donor> findByBloodGroupAndCityAndAvailable(String bloodGroup, String city, Boolean available);
    
    @Query("SELECT d FROM Donor d WHERE d.bloodGroup = :bloodGroup AND d.city LIKE %:city% AND d.available = true")
    List<Donor> searchAvailableDonors(@Param("bloodGroup") String bloodGroup, @Param("city") String city);
    
    List<Donor> findByAvailable(Boolean available);
    
    Long countByBloodGroup(String bloodGroup);
    
    Long countByCity(String city);
}
