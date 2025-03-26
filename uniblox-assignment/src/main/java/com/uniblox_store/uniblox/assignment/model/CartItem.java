package com.uniblox_store.uniblox.assignment.model;

import lombok.Data;

@Data
public class CartItem {
    private Product product;
    private int quantity;
}
