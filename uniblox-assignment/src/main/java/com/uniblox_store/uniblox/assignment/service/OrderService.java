package com.uniblox_store.uniblox.assignment.service;

import com.uniblox_store.uniblox.assignment.model.Cart;
import com.uniblox_store.uniblox.assignment.model.CartItem;
import com.uniblox_store.uniblox.assignment.model.Coupon;
import com.uniblox_store.uniblox.assignment.model.Order;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final CartService cartService;
    private final CouponService couponService;
    private final List<Order> orders = new ArrayList<>();

    private final int nthOrder = 3;  // Every 3rd order gets a coupon
    private int orderSequence = 1;

    public OrderService(CartService cartService, CouponService couponService) {
        this.cartService = cartService;
        this.couponService = couponService;
    }

    // Checkout flow
    public Order checkout(String userId, String couponId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot checkout.");
        }

        double totalAmount = cartService.calculateCartTotal(userId);
        double discount = 0.0;

        // Validate and apply coupon if provided
        if (couponId != null && !couponId.isEmpty()) {
            if (couponService.validateCoupon(couponId)) {
                discount = couponService.applyCoupon(couponId, totalAmount);
            } else {
                throw new RuntimeException("Invalid or expired coupon");
            }
        }

        double orderValue = totalAmount - discount;

        // Create and store order
        Order order = new Order();
        order.setId("ORDER-" + orderSequence++);
        order.setUserId(userId);
        order.setProducts(new ArrayList<>(cart.getItems()));
        order.setTotalAmount(totalAmount);
        order.setCouponId(couponId);
        order.setDiscount(discount);
        order.setOrderValue(orderValue);

        orders.add(order);

        // Clear the cart
        cartService.clearCart(userId);

        // Nth Order -> Generate Coupon
        if (orders.size() % nthOrder == 0) {
            Coupon newCoupon = couponService.generateCoupon();
            System.out.println("🎉 Congrats! Generated new coupon: " + newCoupon.getId());
        }

        return order;
    }

    // Get all orders - for Admin
    public List<Order> getAllOrders() {
        return orders;
    }

    // Admin reports
    public double getTotalPurchaseAmount() {
        return orders.stream().mapToDouble(Order::getOrderValue).sum();
    }

    public int getTotalItemsSold() {
        return orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
