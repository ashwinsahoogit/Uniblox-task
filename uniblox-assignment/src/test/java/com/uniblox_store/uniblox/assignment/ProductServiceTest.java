package com.uniblox_store.uniblox.assignment;

import static org.junit.jupiter.api.Assertions.*;

import com.uniblox_store.uniblox.assignment.model.Product;
import com.uniblox_store.uniblox.assignment.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private static final String TEST_PRODUCT_ID = "p1";
    private static final String TEST_PRODUCT_NAME = "Test Product";
    private static final String TEST_PRODUCT_DESC = "Test Description";
    private static final double TEST_PRODUCT_PRICE = 100.0;

    @BeforeEach
    void setUp() {
        productService.clear(); // Reset to initial state before each test
    }

    @Test
    void testGetProductById_ExistingProduct() {
        Product product = productService.getProductById(TEST_PRODUCT_ID);
        
        assertNotNull(product);
        assertEquals(TEST_PRODUCT_ID, product.getId());
        assertEquals("Laptop", product.getName());
        assertEquals("Gaming Laptop", product.getDescription());
        assertEquals(1000.0, product.getAmount());
    }

    @Test
    void testGetProductById_NonExistingProduct() {
        Product product = productService.getProductById("non-existent");
        
        assertNull(product);
    }

    @Test
    void testGetProductById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductById(null);
        });
    }

    @Test
    void testGetProductById_EmptyId() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductById("");
        });
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = productService.getAllProducts();
        
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(3, products.size()); // Based on initialization in ProductService
    }

    @Test
    void testAddProduct() {
        Product newProduct = new Product("p4", TEST_PRODUCT_NAME, TEST_PRODUCT_DESC, TEST_PRODUCT_PRICE);
        
        productService.addProduct(newProduct);
        
        Product retrievedProduct = productService.getProductById("p4");
        assertNotNull(retrievedProduct);
        assertEquals(TEST_PRODUCT_NAME, retrievedProduct.getName());
        assertEquals(TEST_PRODUCT_DESC, retrievedProduct.getDescription());
        assertEquals(TEST_PRODUCT_PRICE, retrievedProduct.getAmount());
    }

    @Test
    void testAddProduct_NullProduct() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(null);
        });
    }

    @Test
    void testAddProduct_InvalidProduct() {
        // Test with null ID
        assertThrows(Exception.class, () -> {
            productService.addProduct(new Product(null, TEST_PRODUCT_NAME, TEST_PRODUCT_DESC, TEST_PRODUCT_PRICE));
        });

        // Test with empty name
        assertThrows(Exception.class, () -> {
            productService.addProduct(new Product("p4", "", TEST_PRODUCT_DESC, TEST_PRODUCT_PRICE));
        });

        // Test with negative amount
        assertThrows(Exception.class, () -> {
            productService.addProduct(new Product("p4", TEST_PRODUCT_NAME, TEST_PRODUCT_DESC, -100));
        });
    }

    @Test
    void testAddProduct_OverwriteExisting() {
        // Create a new product with the same ID
        Product updatedProduct = new Product(TEST_PRODUCT_ID, "Updated Name", "Updated Desc", 200.0);
        
        productService.addProduct(updatedProduct);
        
        Product retrievedProduct = productService.getProductById(TEST_PRODUCT_ID);
        assertNotNull(retrievedProduct);
        assertEquals("Updated Name", retrievedProduct.getName());
        assertEquals("Updated Desc", retrievedProduct.getDescription());
        assertEquals(200.0, retrievedProduct.getAmount());
    }
} 