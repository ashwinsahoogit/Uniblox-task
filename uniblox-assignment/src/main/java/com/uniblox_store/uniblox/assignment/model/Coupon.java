package com.uniblox_store.uniblox.assignment.model;

import lombok.Data;

@Data
public class Coupon {
    private String id;
    private String name;
    private double discountPercentage;
    private boolean isUsed;
}
