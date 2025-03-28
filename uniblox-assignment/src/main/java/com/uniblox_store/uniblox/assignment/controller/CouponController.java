package com.uniblox_store.uniblox.assignment.controller;

import com.uniblox_store.uniblox.assignment.model.Coupon;
import com.uniblox_store.uniblox.assignment.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // Endpoint to generate a new coupon (Admin Only)
    @PostMapping("/generate")
    public ResponseEntity<Coupon> generateCoupon() {
        Coupon coupon = couponService.generateCoupon();
        return ResponseEntity.ok(coupon);
    }

    // Endpoint to validate a coupon
    @GetMapping("/validate/{couponId}")
    public ResponseEntity<Boolean> validateCoupon(@PathVariable String couponId) {
        boolean isValid = couponService.validateCoupon(couponId);
        return ResponseEntity.ok(isValid);
    }

    // Get all coupons (Admin Only)
    @GetMapping("/all")
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }
}
