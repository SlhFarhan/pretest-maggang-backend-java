package com.farhansolih.ecommerce.controller;

import com.farhansolih.ecommerce.model.User;
import com.farhansolih.ecommerce.repository.UserRepository; // Langsung import repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")

public class UserController {

    @Autowired
    private UserRepository userRepository;
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(UUID.randomUUID());
        return userRepository.save(user);

    }

}