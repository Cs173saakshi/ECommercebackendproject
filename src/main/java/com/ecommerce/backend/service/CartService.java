package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.CartDTO;
import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.entity.User;

public interface CartService {
    Cart addItemToCart(User user, CartDTO dto);
    Cart getCartByUser(User user);
}
