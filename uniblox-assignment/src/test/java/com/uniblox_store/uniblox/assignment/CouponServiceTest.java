package com.uniblox_store.uniblox.assignment;

import static org.junit.jupiter.api.Assertions.*;

import com.uniblox_store.uniblox.assignment.model.Coupon;
import com.uniblox_store.uniblox.assignment.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    private Coupon validCoupon;
    private Coupon usedCoupon;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        validCoupon = new Coupon("valid-coupon", "DISCOUNT10", 10.0, false);
        usedCoupon = new Coupon("used-coupon", "DISCOUNT20", 20.0, true);
        Arrays.asList(validCoupon, usedCoupon);
    }

    @Test
    void testGenerateCoupon() {
        Coupon coupon = couponService.generateCoupon();
        
        assertNotNull(coupon);
        assertNotNull(coupon.getId());
        assertEquals("DISCOUNT10", coupon.getName());
        assertEquals(10.0, coupon.getDiscountPercentage());
        assertFalse(coupon.isUsed());
    }

    @Test
    void testValidateCoupon_ValidCoupon() {
        // First generate a coupon
        Coupon coupon = couponService.generateCoupon();
        
        // Then validate it
        boolean isValid = couponService.validateCoupon(coupon.getId());
        assertTrue(isValid);
    }

    @Test
    void testValidateCoupon_UsedCoupon() {
        // First generate a coupon
        Coupon coupon = couponService.generateCoupon();
        
        // Apply the coupon to mark it as used
        couponService.applyCoupon(coupon.getId(), 100.0);
        
        // Then validate it
        boolean isValid = couponService.validateCoupon(coupon.getId());
        assertFalse(isValid);
    }

    @Test
    void testValidateCoupon_NonExistentCoupon() {
        boolean isValid = couponService.validateCoupon("non-existent");
        assertFalse(isValid);
    }

    @Test
    void testApplyCoupon_ValidCoupon() {
        // First generate a coupon
        Coupon coupon = couponService.generateCoupon();
        
        // Apply the coupon
        double discount = couponService.applyCoupon(coupon.getId(), 100.0);
        assertEquals(10.0, discount); // 10% of 100.0
    }

    @Test
    void testApplyCoupon_UsedCoupon() {
        // First generate a coupon
        Coupon coupon = couponService.generateCoupon();
        
        // Apply the coupon first time
        couponService.applyCoupon(coupon.getId(), 100.0);
        
        // Try to apply it again
        double discount = couponService.applyCoupon(coupon.getId(), 100.0);
        assertEquals(0.0, discount);
    }

    @Test
    void testGetAllCoupons() {
        // Generate some coupons
        Coupon coupon1 = couponService.generateCoupon();
        Coupon coupon2 = couponService.generateCoupon();
        
        // Apply one coupon to mark it as used
        couponService.applyCoupon(coupon1.getId(), 100.0);
        
        List<Coupon> coupons = couponService.getAllCoupons();
        assertNotNull(coupons);
        assertEquals(2, coupons.size());
        assertTrue(coupons.contains(coupon1));
        assertTrue(coupons.contains(coupon2));
    }

    @Test
    void testGetTotalDiscountGiven() {
        // Generate and apply some coupons
        Coupon coupon1 = couponService.generateCoupon();
        Coupon coupon2 = couponService.generateCoupon();
        
        // Apply both coupons to mark them as used
        couponService.applyCoupon(coupon1.getId(), 100.0);
        couponService.applyCoupon(coupon2.getId(), 200.0);
        
        // getTotalDiscountGiven returns the sum of discount percentages (10% + 10% = 20%)
        double totalDiscount = couponService.getTotalDiscountGiven();
        assertEquals(20.0, totalDiscount);
    }
} 