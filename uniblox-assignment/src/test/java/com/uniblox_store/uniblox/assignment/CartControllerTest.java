package com.uniblox_store.uniblox.assignment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniblox_store.uniblox.assignment.dto.CartItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Clear any existing cart data before each test
        try {
            mockMvc.perform(delete("/cart/clear")
                    .param("userId", "user123"));
        } catch (Exception e) {
            // Ignore if cart doesn't exist
        }
    }

    @Test
    void testAddItemToCart_Success() throws Exception {
        CartItemRequest request = new CartItemRequest();
        request.setUserId("user123");
        request.setProductId("p1"); // Using a product ID that exists in InMemoryStore
        request.setQuantity(2);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }

    @Test
    void testAddItemToCart_InvalidProduct() throws Exception {
        CartItemRequest request = new CartItemRequest();
        request.setUserId("user123");
        request.setProductId("invalid-product");
        request.setQuantity(2);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddItemToCart_InvalidQuantity() throws Exception {
        CartItemRequest request = new CartItemRequest();
        request.setUserId("user123");
        request.setProductId("p1");
        request.setQuantity(0); // Invalid quantity

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddMultipleProducts() throws Exception {
        // Add first product
        CartItemRequest request1 = new CartItemRequest();
        request1.setUserId("user123");
        request1.setProductId("p1");
        request1.setQuantity(2);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)));

        // Add second product
        CartItemRequest request2 = new CartItemRequest();
        request2.setUserId("user123");
        request2.setProductId("p2");
        request2.setQuantity(1);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)));

        // Verify cart contents
        mockMvc.perform(get("/cart/view")
                .param("userId", "user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].product.id").value("p1"))
                .andExpect(jsonPath("$.items[1].product.id").value("p2"));
    }

    @Test
    void testUpdateExistingItemQuantity() throws Exception {
        // Add product first time
        CartItemRequest request1 = new CartItemRequest();
        request1.setUserId("user123");
        request1.setProductId("p1");
        request1.setQuantity(2);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)));

        // Add same product again
        CartItemRequest request2 = new CartItemRequest();
        request2.setUserId("user123");
        request2.setProductId("p1");
        request2.setQuantity(3);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)));

        // Verify quantity was updated
        mockMvc.perform(get("/cart/view")
                .param("userId", "user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].quantity").value(5));
    }

    @Test
    void testViewCart() throws Exception {
        // First add an item to the cart
        CartItemRequest request = new CartItemRequest();
        request.setUserId("user123");
        request.setProductId("p1");
        request.setQuantity(2);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Then view the cart
        mockMvc.perform(get("/cart/view")
                .param("userId", "user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }

    @Test
    void testGetCartTotal() throws Exception {
        // First add an item to the cart
        CartItemRequest request = new CartItemRequest();
        request.setUserId("user123");
        request.setProductId("p1"); // Product with price 1000
        request.setQuantity(2);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Then get the total
        mockMvc.perform(get("/cart/total")
                .param("userId", "user123"))
                .andExpect(status().isOk())
                .andExpect(content().string("2000.0")); // 2 items * 1000 price
    }

    @Test
    void testGetEmptyCartTotal() throws Exception {
        mockMvc.perform(get("/cart/total")
                .param("userId", "user123"))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));
    }

    @Test
    void testClearCart() throws Exception {
        // First add an item to the cart
        CartItemRequest request = new CartItemRequest();
        request.setUserId("user123");
        request.setProductId("p1");
        request.setQuantity(2);

        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Then clear the cart
        mockMvc.perform(delete("/cart/clear")
                .param("userId", "user123"))
                .andExpect(status().isOk());

        // Verify cart is empty
        mockMvc.perform(get("/cart/view")
                .param("userId", "user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isEmpty());
    }
}
