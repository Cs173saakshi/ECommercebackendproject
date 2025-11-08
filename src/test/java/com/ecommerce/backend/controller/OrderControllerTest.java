package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.OrderDTO;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.service.OrderService;
import com.ecommerce.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderController orderController;

    private User user;
    private Order order;
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("customer1");

        order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(Order.OrderStatus.PLACED);
        order.setPaymentStatus(Order.PaymentStatus.PENDING);

        userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password("password")
                .authorities("ROLE_CUSTOMER")
                .build();
    }

    @Test
    public void testPlaceOrder() {
        OrderDTO dto = new OrderDTO();
        dto.setTotalAmount(100.0);
        dto.setOrderStatus("PLACED");
        dto.setPaymentStatus("PENDING");

        when(userService.findByUsername(userDetails.getUsername())).thenReturn(user);
        when(orderService.placeOrder(user, dto)).thenReturn(order);

        ResponseEntity<Order> response = orderController.placeOrder(userDetails, dto);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        verify(orderService, times(1)).placeOrder(user, dto);
    }

    @Test
    public void testGetMyOrders() {
        when(userService.findByUsername(userDetails.getUsername())).thenReturn(user);
        when(orderService.getOrdersByUser(user)).thenReturn(List.of(order));

        ResponseEntity<List<Order>> response = orderController.getMyOrders(userDetails);

        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getId()).isEqualTo(1L);
        verify(orderService, times(1)).getOrdersByUser(user);
    }

    @Test
    public void testGetOrdersByUserId() {
        Long userId = 1L;
        when(orderService.getOrdersByUserId(userId)).thenReturn(List.of(order));

        ResponseEntity<List<Order>> response = orderController.getOrdersByUserId(userId);

        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getId()).isEqualTo(1L);
        verify(orderService, times(1)).getOrdersByUserId(userId);
    }
}
