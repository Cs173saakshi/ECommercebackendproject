package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Cart;

public interface CartService {
    Cart addItemToCart(Long userId, Long productId, int quantity);
    Cart getCartByUserId(Long userId);
}
