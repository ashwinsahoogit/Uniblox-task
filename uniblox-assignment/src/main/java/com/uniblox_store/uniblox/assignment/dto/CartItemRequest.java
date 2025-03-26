package com.uniblox_store.uniblox.assignment.dto;

import lombok.Data;

@Data
public class CartItemRequest {
    private String userId;
    private String productId;
    private int quantity;
}
