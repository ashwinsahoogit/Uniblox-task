package com.uniblox_store.uniblox.assignment.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.util.List;

@Data
public class Order {
    @NotNull(message = "Order ID cannot be null")
    @NotBlank(message = "Order ID cannot be empty")
    private String id;

    @NotNull(message = "User ID cannot be null")
    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotNull(message = "Products list cannot be null")
    @Valid
    private List<CartItem> products;

    @NotNull(message = "Total amount cannot be null")
    @Positive(message = "Total amount must be positive")
    private double totalAmount;

    private String couponId;

    @PositiveOrZero(message = "Discount must be zero or positive")
    private double discount; // Calculated if coupon applied

    @NotNull(message = "Order value cannot be null")
    @Positive(message = "Order value must be positive")
    private double orderValue; // Final amount after discount
}
