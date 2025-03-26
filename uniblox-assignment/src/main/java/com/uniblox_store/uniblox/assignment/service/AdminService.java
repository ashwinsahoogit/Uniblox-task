package com.uniblox_store.uniblox.assignment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uniblox_store.uniblox.assignment.model.Coupon;
import com.uniblox_store.uniblox.assignment.model.Order;

@Service
public class AdminService {

    private final OrderService orderService;
    private final CouponService couponService;

    public AdminService(OrderService orderService, CouponService couponService) {
        this.orderService = orderService;
        this.couponService = couponService;
    }

    public int getTotalItemsPurchased() {
        return orderService.getTotalItemsSold();
    }

    public double getTotalPurchaseAmount() {
        return orderService.getTotalPurchaseAmount();
    }

    public List<Coupon> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    public double getTotalDiscountGiven() {
        return orderService.getAllOrders()
                .stream()
                .mapToDouble(Order::getDiscount)
                .sum();
    }
}
