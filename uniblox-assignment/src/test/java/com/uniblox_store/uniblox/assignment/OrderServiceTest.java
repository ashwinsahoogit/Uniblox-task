package com.uniblox_store.uniblox.assignment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.uniblox_store.uniblox.assignment.model.Cart;
import com.uniblox_store.uniblox.assignment.model.CartItem;
import com.uniblox_store.uniblox.assignment.model.Order;
import com.uniblox_store.uniblox.assignment.model.Product;
import com.uniblox_store.uniblox.assignment.service.CartService;
import com.uniblox_store.uniblox.assignment.service.CouponService;
import com.uniblox_store.uniblox.assignment.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

class OrderServiceTest {

    @Mock
    private CartService cartService;

    @Mock
    private CouponService couponService;

    @InjectMocks
    private OrderService orderService;

    private static final String TEST_USER = "testUser";
    private static final String VALID_COUPON = "valid-coupon";
    private static final String INVALID_COUPON = "invalid-coupon";
    private static final String PRODUCT_ID = "p1";
    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup test product
        testProduct = new Product(PRODUCT_ID, "Test Product", "Description", 100.0);
        
        // Setup cart with items
        Cart cart = new Cart(TEST_USER, new ArrayList<>());
        CartItem cartItem = new CartItem(testProduct, 2);
        cart.getItems().add(cartItem);
        
        // Mock cart service behavior
        when(cartService.getCartByUserId(TEST_USER)).thenReturn(cart);
        when(cartService.calculateCartTotal(TEST_USER)).thenReturn(200.0);
    }

    @Test
    void testCheckoutWithoutCoupon() {
        Order order = orderService.checkout(TEST_USER, null);

        assertNotNull(order);
        assertEquals(200.0, order.getTotalAmount());
        assertEquals(200.0, order.getOrderValue());
        assertEquals(0.0, order.getDiscount());
        assertEquals(TEST_USER, order.getUserId());
        assertEquals(1, order.getProducts().size());
        assertEquals(2, order.getProducts().get(0).getQuantity());
    }

    @Test
    void testCheckoutWithValidCoupon() {
        double discount = 20.0; // 10% discount on 200.0

        when(couponService.validateCoupon(VALID_COUPON)).thenReturn(true);
        when(couponService.applyCoupon(VALID_COUPON, 200.0)).thenReturn(discount);

        Order order = orderService.checkout(TEST_USER, VALID_COUPON);

        assertNotNull(order);
        assertEquals(200.0, order.getTotalAmount());
        assertEquals(discount, order.getDiscount());
        assertEquals(180.0, order.getOrderValue());
        assertEquals(VALID_COUPON, order.getCouponId());
        assertEquals(TEST_USER, order.getUserId());
        assertEquals(1, order.getProducts().size());
    }

    @Test
    void testCheckoutWithInvalidCoupon() {
        when(couponService.validateCoupon(INVALID_COUPON)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> 
            orderService.checkout(TEST_USER, INVALID_COUPON)
        );
    }

    @Test
    void testCheckoutWithEmptyCart() {
        when(cartService.getCartByUserId(TEST_USER)).thenReturn(new Cart(TEST_USER, new ArrayList<>()));

        assertThrows(RuntimeException.class, () -> 
            orderService.checkout(TEST_USER, null)
        );
    }
}
