package com.farhansolih.ecommerce.service;

import com.farhansolih.ecommerce.model.Cart;
import com.farhansolih.ecommerce.model.Product;
import com.farhansolih.ecommerce.model.User;
import com.farhansolih.ecommerce.repository.CartRepository;
import com.farhansolih.ecommerce.repository.ProductRepository;
import com.farhansolih.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Cart> getCartByUserId(UUID userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addProductToCart(UUID userId, UUID productId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceAccessException("User tidak ditemukan"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceAccessException("Produk tidak ditemukan"));

        Optional<Cart> existingCartItem = cartRepository.findByUserIdAndProductId(userId, productId);
        if (existingCartItem.isPresent()) {
            Cart cart = existingCartItem.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            return cartRepository.save(cart);
        } else {
            Cart newCartItem = new Cart();
            newCartItem.setId(UUID.randomUUID());
            newCartItem.setUser(user);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            return cartRepository.save(newCartItem);
        }
    }

    public void removeItemFromCart(UUID cartItemId) {
        cartRepository.deleteById(cartItemId);
    }
}