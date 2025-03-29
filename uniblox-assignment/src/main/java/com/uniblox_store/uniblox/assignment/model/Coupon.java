package com.uniblox_store.uniblox.assignment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    @NotNull(message = "Coupon ID cannot be null")
    @NotBlank(message = "Coupon ID cannot be empty")
    private String id;

    @NotNull(message = "Coupon name cannot be null")
    @NotBlank(message = "Coupon name cannot be empty")
    private String name;

    @NotNull(message = "Discount percentage cannot be null")
    @PositiveOrZero(message = "Discount percentage must be zero or positive")
    private double discountPercentage;

    private boolean isUsed;
}
