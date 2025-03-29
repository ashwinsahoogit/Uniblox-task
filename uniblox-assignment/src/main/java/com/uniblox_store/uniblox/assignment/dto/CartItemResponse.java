package com.uniblox_store.uniblox.assignment.dto;

import lombok.Data;

@Data
public class CartItemResponse {
    private String productId;
    private String productName;
    private double productPrice;
    private int quantity;
    private double subtotal;
} 