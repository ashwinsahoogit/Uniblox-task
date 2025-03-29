package com.uniblox_store.uniblox.assignment;

import static org.junit.jupiter.api.Assertions.*;

import com.uniblox_store.uniblox.assignment.model.Coupon;
import com.uniblox_store.uniblox.assignment.service.CouponService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    private static final String TEST_COUPON_NAME = "DISCOUNT10";
    private static final double TEST_DISCOUNT_PERCENTAGE = 10.0;

    @BeforeEach
    void setUp() {
        // Clear the coupon store before each test
        couponService.getAllCoupons().forEach(coupon -> 
            couponService.getCouponById(coupon.getId()).setUsed(false)
        );
    }

    @Test
    void testGenerateCoupon() {
        Coupon coupon = couponService.generateCoupon();
        
        assertNotNull(coupon);
        assertNotNull(coupon.getId());
        assertEquals(TEST_COUPON_NAME, coupon.getName());
        assertEquals(TEST_DISCOUNT_PERCENTAGE, coupon.getDiscountPercentage());
        assertFalse(coupon.isUsed());
    }

    @Test
    void testValidateCoupon_ValidCoupon() {
        Coupon coupon = couponService.generateCoupon();
        
        boolean isValid = couponService.validateCoupon(coupon.getId());
        
        assertTrue(isValid);
    }

    @Test
    void testValidateCoupon_InvalidCoupon() {
        boolean isValid = couponService.validateCoupon("non-existent-coupon");
        
        assertFalse(isValid);
    }

    @Test
    void testValidateCoupon_UsedCoupon() {
        Coupon coupon = couponService.generateCoupon();
        couponService.applyCoupon(coupon.getId(), 100.0);
        
        boolean isValid = couponService.validateCoupon(coupon.getId());
        
        assertFalse(isValid);
    }

    @Test
    void testApplyCoupon_ValidCoupon() {
        Coupon coupon = couponService.generateCoupon();
        double totalAmount = 100.0;
        double expectedDiscount = (totalAmount * TEST_DISCOUNT_PERCENTAGE) / 100;
        
        double discount = couponService.applyCoupon(coupon.getId(), totalAmount);
        
        assertEquals(expectedDiscount, discount);
        assertTrue(coupon.isUsed());
    }

    @Test
    void testApplyCoupon_InvalidCoupon() {
        double totalAmount = 100.0;
        
        double discount = couponService.applyCoupon("non-existent-coupon", totalAmount);
        
        assertEquals(0.0, discount);
    }

    @Test
    void testApplyCoupon_UsedCoupon() {
        Coupon coupon = couponService.generateCoupon();
        double totalAmount = 100.0;
        
        // First application
        couponService.applyCoupon(coupon.getId(), totalAmount);
        
        // Second application
        double discount = couponService.applyCoupon(coupon.getId(), totalAmount);
        
        assertEquals(0.0, discount);
    }

    @Test
    void testGetAllCoupons() {
        // Generate some test coupons
        Coupon coupon1 = couponService.generateCoupon();
        Coupon coupon2 = couponService.generateCoupon();
        
        List<Coupon> coupons = couponService.getAllCoupons();
        
        assertNotNull(coupons);
        assertTrue(coupons.size() >= 2);
        assertTrue(coupons.contains(coupon1));
        assertTrue(coupons.contains(coupon2));
    }

    @Test
    void testGetCouponById() {
        Coupon generatedCoupon = couponService.generateCoupon();
        
        Coupon retrievedCoupon = couponService.getCouponById(generatedCoupon.getId());
        
        assertNotNull(retrievedCoupon);
        assertEquals(generatedCoupon.getId(), retrievedCoupon.getId());
        assertEquals(generatedCoupon.getName(), retrievedCoupon.getName());
        assertEquals(generatedCoupon.getDiscountPercentage(), retrievedCoupon.getDiscountPercentage());
        assertEquals(generatedCoupon.isUsed(), retrievedCoupon.isUsed());
    }

    @Test
    void testGetCouponById_NonExistent() {
        Coupon coupon = couponService.getCouponById("non-existent-coupon");
        
        assertNull(coupon);
    }

    @Test
    void testGetTotalDiscountGiven() {
        // Generate and use some coupons
        Coupon coupon1 = couponService.generateCoupon();
        Coupon coupon2 = couponService.generateCoupon();
        
        couponService.applyCoupon(coupon1.getId(), 100.0);
        couponService.applyCoupon(coupon2.getId(), 100.0);
        
        double totalDiscountPercentage = couponService.getTotalDiscountGiven();
        
        assertEquals(20.0, totalDiscountPercentage); // 10% + 10%
    }
} 