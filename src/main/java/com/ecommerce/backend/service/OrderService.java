package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderDTO;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.User;

import java.util.List;

public interface OrderService {

    Order placeOrder(User user, OrderDTO dto);

    List<Order> getOrdersByUser(User user);

    List<Order> getOrdersByUserId(Long userId);  }
