package com.uniblox_store.uniblox.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemRequest {
    @NotNull(message = "User ID cannot be null")
    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotNull(message = "Product ID cannot be null")
    @NotBlank(message = "Product ID cannot be empty")
    private String productId;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private int quantity;
}
