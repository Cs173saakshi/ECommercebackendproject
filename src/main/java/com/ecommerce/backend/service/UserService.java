package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User findByUsername(String username);
}
