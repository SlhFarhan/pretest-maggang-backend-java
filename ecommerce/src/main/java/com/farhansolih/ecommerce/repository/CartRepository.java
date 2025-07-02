package com.farhansolih.ecommerce.repository;

import com.farhansolih.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    List<Cart> findByUserId(UUID userId);
    Optional<Cart> findByUserIdAndProductId(UUID userId, UUID productId);
    void deleteByUserId(UUID userId);
}