package com.uniblox_store.uniblox.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequest {
    @NotNull(message = "Product name cannot be null")
    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotNull(message = "Product description cannot be null")
    @NotBlank(message = "Product description cannot be empty")
    private String description;

    @NotNull(message = "Product amount cannot be null")
    @Positive(message = "Product amount must be positive")
    private double amount;
} 