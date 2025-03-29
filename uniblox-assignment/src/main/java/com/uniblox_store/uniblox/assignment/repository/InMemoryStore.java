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
        // Electronics
        productMap.put("p1", new Product("p1", "Laptop", "Gaming Laptop", 1000));
        productMap.put("p2", new Product("p2", "Phone", "Smartphone", 500));
        productMap.put("p3", new Product("p3", "Headphones", "Noise Cancelling", 200));
        
        // Accessories
        productMap.put("p4", new Product("p4", "Mouse", "Wireless Gaming Mouse", 50));
        productMap.put("p5", new Product("p5", "Keyboard", "Mechanical RGB Keyboard", 120));
        productMap.put("p6", new Product("p6", "Mousepad", "Extended Gaming Mousepad", 25));
        
        // Audio
        productMap.put("p7", new Product("p7", "Speakers", "Bluetooth Speakers", 150));
        productMap.put("p8", new Product("p8", "Microphone", "USB Condenser Mic", 80));
        
        // Storage
        productMap.put("p9", new Product("p9", "SSD", "1TB NVMe SSD", 100));
        productMap.put("p10", new Product("p10", "External HDD", "2TB Portable Drive", 80));
        
        // Peripherals
        productMap.put("p11", new Product("p11", "Monitor", "27\" 144Hz Gaming Monitor", 300));
        productMap.put("p12", new Product("p12", "Webcam", "1080p HD Webcam", 60));
    }
}
