package com.uniblox_store.uniblox.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniblox_store.uniblox.assignment.dto.CartItemRequest;
import com.uniblox_store.uniblox.assignment.service.CartService;
import com.uniblox_store.uniblox.assignment.service.CouponService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private CouponService couponService;

    private static final String TEST_USER = "testUser";
    private static final String VALID_PRODUCT_ID = "p1";
    private static final String INVALID_PRODUCT_ID = "invalid_product";
    private String validCouponId;
    private static final String INVALID_COUPON = "INVALID";

    @BeforeEach
    void setUp() {
        // Reset cart before each test
        cartService.clearCart(TEST_USER);
        
        // Generate a valid coupon for testing
        validCouponId = couponService.generateCoupon().getId();
    }

    @Test
    void testCheckout_WithValidCart_ShouldCreateOrder() throws Exception {
        // Add items to cart first
        CartItemRequest request = new CartItemRequest();
        request.setUserId(TEST_USER);
        request.setProductId(VALID_PRODUCT_ID);
        request.setQuantity(2);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Checkout
        mockMvc.perform(post("/order/checkout")
                .param("userId", TEST_USER)
                .param("couponId", "")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(TEST_USER))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.totalAmount").exists())
                .andExpect(jsonPath("$.orderValue").exists());
    }

    @Test
    void testCheckout_WithEmptyCart_ShouldReturnError() throws Exception {
        mockMvc.perform(post("/order/checkout")
                .param("userId", TEST_USER)
                .param("couponId", "")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCheckout_WithInvalidProduct_ShouldReturnError() throws Exception {
        // Add invalid product to cart
        CartItemRequest request = new CartItemRequest();
        request.setUserId(TEST_USER);
        request.setProductId(INVALID_PRODUCT_ID);
        request.setQuantity(1);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        // Attempt checkout
        mockMvc.perform(post("/order/checkout")
                .param("userId", TEST_USER)
                .param("couponId", "")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCheckout_WithInvalidQuantity_ShouldReturnError() throws Exception {
        // Add item with invalid quantity
        CartItemRequest request = new CartItemRequest();
        request.setUserId(TEST_USER);
        request.setProductId(VALID_PRODUCT_ID);
        request.setQuantity(-1); // Invalid quantity

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCheckout_WithValidCoupon_ShouldApplyDiscount() throws Exception {
        // Add items to cart
        CartItemRequest request = new CartItemRequest();
        request.setUserId(TEST_USER);
        request.setProductId(VALID_PRODUCT_ID);
        request.setQuantity(2);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Checkout with valid coupon
        mockMvc.perform(post("/order/checkout")
                .param("userId", TEST_USER)
                .param("couponId", validCouponId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discount").exists())
                .andExpect(jsonPath("$.orderValue").exists())
                .andExpect(jsonPath("$.orderValue").isNumber());
    }

    @Test
    void testCheckout_WithInvalidCoupon_ShouldReturnError() throws Exception {
        // Add items to cart
        CartItemRequest request = new CartItemRequest();
        request.setUserId(TEST_USER);
        request.setProductId(VALID_PRODUCT_ID);
        request.setQuantity(2);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Checkout with invalid coupon
        mockMvc.perform(post("/order/checkout")
                .param("userId", TEST_USER)
                .param("couponId", INVALID_COUPON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCheckout_WithNonExistentUser_ShouldReturnError() throws Exception {
        mockMvc.perform(post("/order/checkout")
                .param("userId", "nonExistentUser")
                .param("couponId", "")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
