package com.uniblox_store.uniblox.assignment.controller;

import com.uniblox_store.uniblox.assignment.dto.CartItemRequest;
import com.uniblox_store.uniblox.assignment.model.Cart;
import com.uniblox_store.uniblox.assignment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    //Add an item to the user's cart
    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemRequest request) {
        try {
            Cart cart = cartService.addItemToCart(request.getUserId(), request.getProductId(), request.getQuantity());
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //View cart for a user
    @GetMapping("/view")
    public ResponseEntity<Cart> viewCart(@RequestParam String userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    //Get the total amount of the cart
    @GetMapping("/total")
    public ResponseEntity<Double> getCartTotal(@RequestParam String userId) {
        double total = cartService.calculateCartTotal(userId);
        return ResponseEntity.ok(total);
    }

    //Clear the cart
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestParam String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }
}