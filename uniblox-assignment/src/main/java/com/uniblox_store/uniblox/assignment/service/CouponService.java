package com.uniblox_store.uniblox.assignment.service;

import com.uniblox_store.uniblox.assignment.model.Coupon;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CouponService {

    // In-memory store for all coupons
    private final Map<String, Coupon> couponStore = new HashMap<>();

    // Generate a new coupon with 10% discount
    public Coupon generateCoupon() {
        String couponId = UUID.randomUUID().toString();
        Coupon coupon = new Coupon(couponId, "DISCOUNT10", 10.0, false);
        couponStore.put(couponId, coupon);
        return coupon;
    }

    // Validate if the coupon exists and is not used
    public boolean validateCoupon(String couponId) {
        Coupon coupon = couponStore.get(couponId);
        return coupon != null && !coupon.isUsed();
    }

    // Apply coupon: calculate discount and mark as used
    public double applyCoupon(String couponId, double totalAmount) {
        Coupon coupon = couponStore.get(couponId);
        if (coupon != null && !coupon.isUsed()) {
            double discount = (totalAmount * coupon.getDiscountPercentage()) / 100;
            coupon.setUsed(true);  // Mark coupon as used
            return discount;
        }
        return 0.0;  // No discount if invalid or already used
    }

    // For admin: get the total discount percentage applied (can be customized)
    public double getTotalDiscountGiven() {
        return couponStore.values().stream()
                .filter(Coupon::isUsed)
                .mapToDouble(Coupon::getDiscountPercentage)
                .sum();
    }

    // For admin: list all coupons
    public List<Coupon> getAllCoupons() {
        return new ArrayList<>(couponStore.values());
    }

    // Optional: fetch a coupon by ID
    public Coupon getCouponById(String couponId) {
        return couponStore.get(couponId);
    }
}
