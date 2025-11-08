package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ProductDTO;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("A high-performance laptop");
        product.setPrice(1200.0);
        product.setStock(10);
        product.setCategory("Electronics");
        product.setImageUrl("image.jpg");
        product.setRating(4.5);
    }

    @Test
    public void testAddProduct() {
        when(productService.addProduct(any(Product.class))).thenReturn(product);

        ProductDTO dto = new ProductDTO();
        dto.setName("Laptop");
        dto.setDescription("A high-performance laptop");
        dto.setPrice(1200.0);
        dto.setStock(10);
        dto.setCategory("Electronics");
        dto.setImageUrl("image.jpg");
        dto.setRating(4.5);

        ResponseEntity<Product> response = productController.addProduct(dto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Laptop");
        verify(productService, times(1)).addProduct(any(Product.class));
    }

    @Test
    public void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product));

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Laptop");
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testGetProductById() {
        when(productService.getProductById(1L)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProduct(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void testUpdateProduct() {
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(product);

        ProductDTO dto = new ProductDTO();
        dto.setName("Updated Laptop");
        dto.setDescription("Updated description");
        dto.setPrice(1300.0);
        dto.setStock(8);
        dto.setCategory("Electronics");
        dto.setImageUrl("updated.jpg");
        dto.setRating(4.7);

        ResponseEntity<Product> response = productController.updateProduct(1L, dto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Laptop"); // returned mock
        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(1L);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        verify(productService, times(1)).deleteProduct(1L);
    }
}
