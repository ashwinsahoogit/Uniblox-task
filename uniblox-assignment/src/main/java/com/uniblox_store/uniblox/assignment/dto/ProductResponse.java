package com.uniblox_store.uniblox.assignment.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status; // ACTIVE, INACTIVE, etc.
} 