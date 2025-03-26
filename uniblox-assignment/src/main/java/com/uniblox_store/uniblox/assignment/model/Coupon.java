package com.uniblox_store.uniblox.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    private String id;
    private String name;
    private double discountPercentage;
    private boolean isUsed;
}
