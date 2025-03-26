package com.uniblox_store.uniblox.assignment.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uniblox_store.uniblox.assignment.model.Cart;
import com.uniblox_store.uniblox.assignment.model.Coupon;
import com.uniblox_store.uniblox.assignment.model.Order;
import com.uniblox_store.uniblox.assignment.model.Product;
import com.uniblox_store.uniblox.assignment.model.User;

import jakarta.annotation.PostConstruct;

@Component
public class InMemoryStore {

    public Map<String, User> userMap = new HashMap<>();
    public Map<String, Product> productMap = new HashMap<>();
    public Map<String, Cart> cartMap = new HashMap<>();
    public Map<String, Coupon> couponMap = new HashMap<>();
    public Map<String, Order> orderMap = new HashMap<>();
    public List<Coupon> availableCoupons = new ArrayList<>();
    public int orderCounter = 0;

    @PostConstruct
    public void initProducts() {
        productMap.put("p1", new Product("p1", "Laptop", "Gaming Laptop", 1000));
        productMap.put("p2", new Product("p2", "Phone", "Smartphone", 500));
        productMap.put("p3", new Product("p3", "Headphones", "Noise Cancelling", 200));
    }
}
