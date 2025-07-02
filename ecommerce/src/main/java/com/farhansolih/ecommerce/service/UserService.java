package com.farhansolih.ecommerce.service;

import com.farhansolih.ecommerce.model.User;
import com.farhansolih.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User user) {
        user.setId(UUID.randomUUID());
        return userRepository.save(user);
    }
}