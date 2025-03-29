package com.uniblox_store.uniblox.assignment;

import static org.junit.jupiter.api.Assertions.*;

import com.uniblox_store.uniblox.assignment.exception.GlobalExceptionHandler;
import com.uniblox_store.uniblox.assignment.exception.ProductNotFoundException;
import com.uniblox_store.uniblox.assignment.exception.InvalidCouponException;
import com.uniblox_store.uniblox.assignment.dto.ErrorResponse;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    @Test
    void testHandleProductNotFoundException() {
        // Arrange
        request.setRequestURI("/api/products/123");
        ProductNotFoundException ex = new ProductNotFoundException("Product not found");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleProductNotFoundException(ex, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals("Not Found", response.getBody().getError());
        assertEquals("Product not found", response.getBody().getMessage());
        assertEquals("/api/products/123", response.getBody().getPath());
    }

    @Test
    void testHandleInvalidCouponException() {
        // Arrange
        request.setRequestURI("/api/orders/checkout");
        InvalidCouponException ex = new InvalidCouponException("Invalid coupon code");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleInvalidCouponException(ex, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("Bad Request", response.getBody().getError());
        assertEquals("Invalid coupon code", response.getBody().getMessage());
        assertEquals("/api/orders/checkout", response.getBody().getPath());
    }
} 