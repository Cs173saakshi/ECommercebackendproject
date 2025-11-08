package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.entity.CartItem;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private User user;
    private Product product;
    private Cart cart;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("saakshi");

        product = new Product();
        product.setId(1L);
        product.setName("Boats Earphone");
        product.setPrice(1299.99);

        cart = new Cart(user);
        CartItem item = new CartItem(product, 2);
        cart.setItems(new ArrayList<>());
        cart.getItems().add(item);
        cart.setTotalPrice(product.getPrice() * 2);
    }

    @Test
    public void testAddToCart() {
        CartRequest request = new CartRequest();
        request.setUserId(1L);
        request.setProductId(1L);
        request.setQuantity(2);

        when(cartService.addItemToCart(request.getUserId(), request.getProductId(), request.getQuantity()))
                .thenReturn(cart);

        ResponseEntity<Cart> response = cartController.addToCart(request);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUser().getUsername()).isEqualTo("saakshi");
        assertThat(response.getBody().getItems().get(0).getProduct().getName()).isEqualTo("Boats Earphone");
        assertThat(response.getBody().getItems().get(0).getQuantity()).isEqualTo(2);

        verify(cartService, times(1)).addItemToCart(request.getUserId(), request.getProductId(), request.getQuantity());
    }
}
