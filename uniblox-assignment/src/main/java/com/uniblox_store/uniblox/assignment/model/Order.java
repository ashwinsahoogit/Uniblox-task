package com.uniblox_store.uniblox.assignment.model;

import lombok.Data;
import java.util.List;

@Data
public class Order {
    private String id;
    private String userId;
    private List<CartItem> products;
    private double totalAmount;
    private String couponId;
    private double discount; // Calculated if coupon applied
    private double orderValue; // Final amount after discount
}
