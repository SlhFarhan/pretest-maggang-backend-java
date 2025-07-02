package com.farhansolih.ecommerce.controller;

import com.farhansolih.ecommerce.model.Cart;
import com.farhansolih.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public List<Cart> getCartItems(@PathVariable UUID userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    public Cart addToCart(@PathVariable UUID userId, @RequestParam UUID productId, @RequestParam int quantity) {
        return cartService.addProductToCart(userId, productId, quantity);
    }

    @DeleteMapping("/item/{cartItemId}")
    public void removeFromCart(@PathVariable UUID cartItemId) {
        cartService.removeItemFromCart(cartItemId);
    }
}