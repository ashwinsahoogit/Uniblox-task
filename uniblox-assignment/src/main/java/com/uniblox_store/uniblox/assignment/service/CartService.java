package com.uniblox_store.uniblox.assignment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniblox_store.uniblox.assignment.model.Cart;
import com.uniblox_store.uniblox.assignment.model.CartItem;
import com.uniblox_store.uniblox.assignment.model.Product;
import com.uniblox_store.uniblox.assignment.exception.ProductNotFoundException;
import com.uniblox_store.uniblox.assignment.exception.InvalidQuantityException;

@Service
public class CartService {

    // In-memory storage of carts keyed by userId
    private final Map<String, Cart> userCarts = new HashMap<>();
    private final ProductService productService;

    @Autowired
    public CartService(ProductService productService) {
        this.productService = productService;
    }

    // Add item to cart
    public Cart addItemToCart(String userId, String productId, int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than 0");
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found");
        }

        Cart cart = userCarts.getOrDefault(userId, new Cart(userId, new ArrayList<>()));

        Optional<CartItem> existingItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            // If product exists, update quantity
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            // If not, add new CartItem
            CartItem cartItem = new CartItem(product, quantity);
            cart.getItems().add(cartItem);
        }

        userCarts.put(userId, cart);
        return cart;
    }
    
    //Calculate total amount of cart
    public double calculateCartTotal(String userId) {
        Cart cart = getCartByUserId(userId);
        if (cart == null || cart.getItems().isEmpty()) {
            return 0.0;
        }
    
        double total = 0.0;
        for (CartItem item : cart.getItems()) {
            total += item.getProduct().getAmount() * item.getQuantity();
        }
        return total;
    }
    
    // Get cart by userId
    public Cart getCartByUserId(String userId) {
        return userCarts.getOrDefault(userId, new Cart(userId, new ArrayList<>()));
    }

    // Clear the cart after checkout
    public void clearCart(String userId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().clear();
        userCarts.put(userId, cart);
    }
}
