package com.uniblox_store.uniblox.assignment;

import com.uniblox_store.uniblox.assignment.model.Cart;
import com.uniblox_store.uniblox.assignment.model.CartItem;
import com.uniblox_store.uniblox.assignment.model.Product;
import com.uniblox_store.uniblox.assignment.service.CartService;
import com.uniblox_store.uniblox.assignment.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup common test products
        product1 = new Product("p1", "Product1", "Desc1", 100.0);
        product2 = new Product("p2", "Product2", "Desc2", 50.0);
        
        // Setup common mocks
        when(productService.getProductById("p1")).thenReturn(product1);
        when(productService.getProductById("p2")).thenReturn(product2);
    }

    @Test
    void testAddItemToCart_NewItem_ShouldAddSuccessfully() {
        // Arrange
        String userId = "user1";
        String productId = "p1";
        int quantity = 2;

        // Act
        Cart updatedCart = cartService.addItemToCart(userId, productId, quantity);

        // Assert
        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getItems().size());
        CartItem cartItem = updatedCart.getItems().get(0);
        assertEquals("Product1", cartItem.getProduct().getName());
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    void testAddItemToCart_ExistingItem_ShouldUpdateQuantity() {
        // Arrange
        String userId = "user1";
        String productId = "p1";
        
        // Act
        cartService.addItemToCart(userId, productId, 2);
        Cart updatedCart = cartService.addItemToCart(userId, productId, 3);

        // Assert
        assertEquals(1, updatedCart.getItems().size());
        assertEquals(5, updatedCart.getItems().get(0).getQuantity());
    }

    @Test
    void testAddItemToCart_NonExistentProduct_ShouldThrowException() {
        // Arrange
        String userId = "user1";
        String productId = "non-existent";
        when(productService.getProductById(productId)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            cartService.addItemToCart(userId, productId, 1)
        );
    }

    @Test
    void testCalculateCartTotal_ShouldReturnCorrectTotal() {
        // Arrange
        String userId = "user1";
        cartService.addItemToCart(userId, "p1", 2);
        cartService.addItemToCart(userId, "p2", 1);

        // Act
        double total = cartService.calculateCartTotal(userId);

        // Assert
        assertEquals(250.0, total);
    }

    @Test
    void testCalculateCartTotal_EmptyCart_ShouldReturnZero() {
        // Arrange
        String userId = "user1";

        // Act
        double total = cartService.calculateCartTotal(userId);

        // Assert
        assertEquals(0.0, total);
    }

    @Test
    void testGetCartByUserId_ShouldReturnCorrectCart() {
        // Arrange
        String userId = "user1";
        cartService.addItemToCart(userId, "p1", 2);

        // Act
        Cart cart = cartService.getCartByUserId(userId);

        // Assert
        assertNotNull(cart);
        assertEquals(userId, cart.getUserId());
        assertEquals(1, cart.getItems().size());
    }

    @Test
    void testClearCart_ShouldRemoveCart() {
        // Arrange
        String userId = "user1";
        cartService.addItemToCart(userId, "p1", 2);

        // Act
        cartService.clearCart(userId);

        // Assert
        Cart cart = cartService.getCartByUserId(userId);
        assertTrue(cart.getItems().isEmpty());
    }
}
