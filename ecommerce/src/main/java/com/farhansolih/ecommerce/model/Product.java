package com.farhansolih.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private String imageUrl;
}