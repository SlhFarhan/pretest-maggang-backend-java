package com.farhansolih.ecommerce.service;

import com.farhansolih.ecommerce.model.Product;
import com.farhansolih.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Produk dengan id " + id + " tidak ada"));
    }

    public Product create(Product product) {
        product.setId(UUID.randomUUID());
        return productRepository.save(product);
    }

    public Product edit(Product product) {
        return productRepository.save(product);
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}