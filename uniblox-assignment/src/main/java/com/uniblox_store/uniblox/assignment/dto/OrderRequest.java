package com.uniblox_store.uniblox.assignment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    @NotNull(message = "User ID cannot be null")
    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotNull(message = "Products list cannot be null")
    @Valid
    private List<CartItemRequest> products;

    private String couponId;
} 