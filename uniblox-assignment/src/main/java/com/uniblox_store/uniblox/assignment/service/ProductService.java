package com.uniblox_store.uniblox.assignment.service;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import com.uniblox_store.uniblox.assignment.model.Product;
import com.uniblox_store.uniblox.assignment.repository.InMemoryStore;

@Service
public class ProductService {
    private final Validator validator;
    private final InMemoryStore inMemoryStore;

    public ProductService(Validator validator, InMemoryStore inMemoryStore) {
        this.validator = validator;
        this.inMemoryStore = inMemoryStore;
    }

    public void addProduct(@Valid Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid product: " + violations.iterator().next().getMessage());
        }
        
        inMemoryStore.productMap.put(product.getId(), product);
    }

    public Product getProductById(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        return inMemoryStore.productMap.get(productId);
    }

    public List<Product> getAllProducts() {
        return List.copyOf(inMemoryStore.productMap.values());
    }
}
