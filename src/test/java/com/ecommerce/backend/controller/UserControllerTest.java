package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User();
        user1.setId(1L);
        user1.setUsername("john");
        user1.setEmail("john@example.com");
        user1.setPassword("12345");
        user1.setRole("CUSTOMER");

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("jane");
        user2.setEmail("jane@example.com");
        user2.setPassword("54321");
        user2.setRole("ADMIN");
    }

    @Test
    void testRegisterUser() {
        when(userService.saveUser(user1)).thenReturn(user1);

        ResponseEntity<User> response = userController.registerUser(user1);

        assertThat(response.getBody()).isEqualTo(user1);
        verify(userService, times(1)).saveUser(user1);
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertThat(response.getBody()).hasSize(2).contains(user1, user2);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setUsername("johnny");
        updatedUser.setEmail("johnny@example.com");
        updatedUser.setPassword("11111");
        updatedUser.setRole("CUSTOMER");

        when(userService.updateUser(1L, updatedUser)).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser(1L, updatedUser);

        assertThat(response.getBody().getUsername()).isEqualTo("johnny");
        verify(userService, times(1)).updateUser(1L, updatedUser);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testGetUserByUsername() {
        when(userService.findByUsername("john")).thenReturn(user1);

        ResponseEntity<User> response = userController.getUserByUsername("john");

        assertThat(response.getBody().getUsername()).isEqualTo("john");
        verify(userService, times(1)).findByUsername("john");
    }
}
