package com.uniblox_store.uniblox.assignment;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniblox_store.uniblox.assignment.controller.CartController;
import com.uniblox_store.uniblox.assignment.dto.CartItemRequest;
import com.uniblox_store.uniblox.assignment.model.Cart;
import com.uniblox_store.uniblox.assignment.model.CartItem;
import com.uniblox_store.uniblox.assignment.model.Product;
import com.uniblox_store.uniblox.assignment.service.CartService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    private static final String TEST_USER = "testUser";
    private static final String TEST_PRODUCT_ID = "p1";
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product(TEST_PRODUCT_ID, "Test Product", "Description", 100.0);
    }

    @Test
    void testAddItemToCart_Success() throws Exception {
        // Arrange
        CartItemRequest request = new CartItemRequest();
        request.setUserId(TEST_USER);
        request.setProductId(TEST_PRODUCT_ID);
        request.setQuantity(2);

        when(cartService.addItemToCart(TEST_USER, TEST_PRODUCT_ID, 2))
            .thenReturn(new Cart(TEST_USER, List.of(new CartItem(testProduct, 2))));

        // Act & Assert
        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(TEST_USER))
                .andExpect(jsonPath("$.items[0].product.id").value(TEST_PRODUCT_ID))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }

    @Test
    void testAddItemToCart_InvalidProduct() throws Exception {
        // Arrange
        CartItemRequest request = new CartItemRequest();
        request.setUserId(TEST_USER);
        request.setProductId("invalid-product");
        request.setQuantity(2);

        when(cartService.addItemToCart(TEST_USER, "invalid-product", 2))
            .thenThrow(new RuntimeException("Product not found"));

        // Act & Assert
        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddItemToCart_InvalidQuantity() throws Exception {
        // Arrange
        CartItemRequest request = new CartItemRequest();
        request.setUserId(TEST_USER);
        request.setProductId(TEST_PRODUCT_ID);
        request.setQuantity(-1);

        // Act & Assert
        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCart_Success() throws Exception {
        // Arrange
        Cart cart = new Cart(TEST_USER, List.of(new CartItem(testProduct, 2)));
        when(cartService.getCartByUserId(TEST_USER)).thenReturn(cart);

        // Act & Assert
        mockMvc.perform(get("/cart/view")
                .param("userId", TEST_USER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(TEST_USER))
                .andExpect(jsonPath("$.items[0].product.id").value(TEST_PRODUCT_ID))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }

    @Test
    void testGetCart_EmptyCart() throws Exception {
        // Arrange
        when(cartService.getCartByUserId(TEST_USER))
            .thenReturn(new Cart(TEST_USER, new ArrayList<>()));

        // Act & Assert
        mockMvc.perform(get("/cart/view")
                .param("userId", TEST_USER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(TEST_USER))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items").isEmpty());
    }

    @Test
    void testCalculateCartTotal_Success() throws Exception {
        // Arrange
        when(cartService.calculateCartTotal(TEST_USER)).thenReturn(200.0);

        // Act & Assert
        mockMvc.perform(get("/cart/total")
                .param("userId", TEST_USER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(200.0));
    }

    @Test
    void testClearCart_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/cart/clear")
                .param("userId", TEST_USER))
                .andExpect(status().isOk());
    }
}
