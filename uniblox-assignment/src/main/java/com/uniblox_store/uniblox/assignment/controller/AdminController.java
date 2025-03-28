package com.uniblox_store.uniblox.assignment.controller;

import com.uniblox_store.uniblox.assignment.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final OrderService orderService;

    public AdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Get total purchase amount
    @GetMapping("/total-purchase")
    public ResponseEntity<Double> getTotalPurchaseAmount() {
        double totalAmount = orderService.getTotalPurchaseAmount();
        return ResponseEntity.ok(totalAmount);
    }

    // Get total items sold
    @GetMapping("/total-items")
    public ResponseEntity<Integer> getTotalItemsSold() {
        int totalItems = orderService.getTotalItemsSold();
        return ResponseEntity.ok(totalItems);
    }
}
