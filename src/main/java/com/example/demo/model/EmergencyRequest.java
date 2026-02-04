package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emergency_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Patient name is required")
    private String patientName;
    
    @NotBlank(message = "Blood group is required")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group")
    private String bloodGroup;
    
    @NotNull(message = "Units required is mandatory")
    @Min(value = 1, message = "At least 1 unit required")
    private Integer unitsRequired;
    
    @NotBlank(message = "Hospital name is required")
    private String hospitalName;
    
    @NotBlank(message = "Hospital address is required")
    private String hospitalAddress;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "Contact person is required")
    private String contactPerson;
    
    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String contactNumber;
    
    @NotNull(message = "Required by date is mandatory")
    private LocalDateTime requiredBy;
    
    @Size(max = 1000)
    private String additionalInfo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private RequestStatus status = RequestStatus.OPEN;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum RequestStatus {
        OPEN, IN_PROGRESS, FULFILLED, CANCELLED
    }
}
