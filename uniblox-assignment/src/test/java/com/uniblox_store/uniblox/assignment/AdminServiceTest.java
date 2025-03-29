package com.uniblox_store.uniblox.assignment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.uniblox_store.uniblox.assignment.model.Coupon;
import com.uniblox_store.uniblox.assignment.model.Order;
import com.uniblox_store.uniblox.assignment.service.AdminService;
import com.uniblox_store.uniblox.assignment.service.CouponService;
import com.uniblox_store.uniblox.assignment.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class AdminServiceTest {

    @Mock
    private OrderService orderService;

    @Mock
    private CouponService couponService;

    @InjectMocks
    private AdminService adminService;

    private static final String TEST_COUPON_ID = "test-coupon";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTotalItemsPurchased() {
        when(orderService.getTotalItemsSold()).thenReturn(10);

        int totalItems = adminService.getTotalItemsPurchased();

        assertEquals(10, totalItems);
    }

    @Test
    void testGetTotalPurchaseAmount() {
        when(orderService.getTotalPurchaseAmount()).thenReturn(1000.0);

        double totalAmount = adminService.getTotalPurchaseAmount();

        assertEquals(1000.0, totalAmount);
    }

    @Test
    void testGetAllCoupons() {
        List<Coupon> expectedCoupons = new ArrayList<>();
        Coupon coupon1 = new Coupon(TEST_COUPON_ID, "TEST10", 10.0, false);
        Coupon coupon2 = new Coupon("coupon2", "TEST20", 20.0, true);
        expectedCoupons.add(coupon1);
        expectedCoupons.add(coupon2);

        when(couponService.getAllCoupons()).thenReturn(expectedCoupons);

        List<Coupon> coupons = adminService.getAllCoupons();

        assertNotNull(coupons);
        assertEquals(2, coupons.size());
        assertTrue(coupons.contains(coupon1));
        assertTrue(coupons.contains(coupon2));
    }

    @Test
    void testGetTotalDiscountGiven() {
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setDiscount(50.0);
        Order order2 = new Order();
        order2.setDiscount(30.0);
        orders.add(order1);
        orders.add(order2);

        when(orderService.getAllOrders()).thenReturn(orders);

        double totalDiscount = adminService.getTotalDiscountGiven();

        assertEquals(80.0, totalDiscount);
    }

    @Test
    void testGetTotalDiscountGiven_NoOrders() {
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>());

        double totalDiscount = adminService.getTotalDiscountGiven();

        assertEquals(0.0, totalDiscount);
    }
} 