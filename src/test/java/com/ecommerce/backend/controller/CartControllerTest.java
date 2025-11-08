package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.CartDTO;
import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.service.CartService;
import com.ecommerce.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CartController cartController;

    private User user;
    private Cart cart;
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Mock user
        user = new User();
        user.setId(1L);
        user.setUsername("customer1");

        // Mock cart
        cart = new Cart(user);

        // Mock UserDetails
        userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password("password")
                .authorities("ROLE_CUSTOMER")
                .build();
    }

    @Test
    public void testAddItemToCart() {
        CartDTO dto = new CartDTO();
        dto.setProductId(1L);
        dto.setQuantity(2);

        when(userService.findByUsername(userDetails.getUsername())).thenReturn(user);
        when(cartService.addItemToCart(user, dto)).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.addItem(userDetails, dto);

        assertThat(response.getBody()).isNotNull();
        verify(cartService, times(1)).addItemToCart(user, dto);
    }

    @Test
    public void testGetCart() {
        when(userService.findByUsername(userDetails.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.getCart(userDetails);

        assertThat(response.getBody()).isNotNull();
        verify(cartService, times(1)).getCartByUser(user);
    }
}
