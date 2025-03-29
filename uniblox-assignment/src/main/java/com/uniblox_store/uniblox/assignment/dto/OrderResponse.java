package com.uniblox_store.uniblox.assignment.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private String id;
    private String userId;
    private List<CartItemResponse> products;
    private double totalAmount;
    private String couponId;
    private double discount;
    private double orderValue;
    private LocalDateTime orderDate;
    private String status; // PENDING, COMPLETED, CANCELLED
} 