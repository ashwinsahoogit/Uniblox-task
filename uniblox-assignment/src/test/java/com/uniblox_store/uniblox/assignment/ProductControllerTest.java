package com.uniblox_store.uniblox.assignment;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uniblox_store.uniblox.assignment.controller.ProductController;
import com.uniblox_store.uniblox.assignment.model.Product;
import com.uniblox_store.uniblox.assignment.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product testProduct;
    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        testProduct = new Product("p1", "Test Product", "Test Description", 100.0);
        testProducts = Arrays.asList(
            testProduct,
            new Product("p2", "Product 2", "Description 2", 200.0)
        );
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(testProducts);

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value("p1"))
            .andExpect(jsonPath("$[0].name").value("Test Product"))
            .andExpect(jsonPath("$[1].id").value("p2"))
            .andExpect(jsonPath("$[1].name").value("Product 2"));
    }

    @Test
    void testGetProductById_Success() throws Exception {
        when(productService.getProductById("p1")).thenReturn(testProduct);

        mockMvc.perform(get("/api/products/p1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value("p1"))
            .andExpect(jsonPath("$.name").value("Test Product"))
            .andExpect(jsonPath("$.description").value("Test Description"))
            .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById("nonexistent")).thenReturn(null);

        mockMvc.perform(get("/api/products/nonexistent"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetProductById_InvalidId() throws Exception {
        mockMvc.perform(get("/api/products/"))
            .andExpect(status().isNotFound());
    }
} 