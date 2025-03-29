package com.uniblox_store.uniblox.assignment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import com.uniblox_store.uniblox.assignment.model.Product;

@Service
public class ProductService {
    private final Map<String, Product> products = new HashMap<>();
    private final Validator validator;

    public ProductService(Validator validator) {
        this.validator = validator;
    }

    @PostConstruct
    public void init() {
        // Initialize with test products
        products.put("p1", new Product("p1", "Laptop", "Gaming Laptop", 1000));
        products.put("p2", new Product("p2", "Phone", "Smartphone", 500));
        products.put("p3", new Product("p3", "Headphones", "Noise Cancelling", 200));
    }

    public void clear() {
        products.clear();
        init(); // Reinitialize with default products
    }

    public void addProduct(@Valid Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid product: " + violations.iterator().next().getMessage());
        }
        
        products.put(product.getId(), product);
    }

    public Product getProductById(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        return products.get(productId);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}
