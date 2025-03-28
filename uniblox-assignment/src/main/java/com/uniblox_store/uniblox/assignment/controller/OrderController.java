package com.uniblox_store.uniblox.assignment.controller;

import com.uniblox_store.uniblox.assignment.model.Order;
import com.uniblox_store.uniblox.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //checkout and place an order
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestParam String userId, @RequestParam(required = false) String couponId) {
        try {
            Order order = orderService.checkout(userId, couponId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //get all orders
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    //get total purchase amount
    @GetMapping("/total-amount")
    public ResponseEntity<Double> getTotalPurchaseAmount() {
        return ResponseEntity.ok(orderService.getTotalPurchaseAmount());
    }

    //get total items sold
    @GetMapping("/total-items")
    public ResponseEntity<Integer> getTotalItemsSold() {
        return ResponseEntity.ok(orderService.getTotalItemsSold());
    }
}
