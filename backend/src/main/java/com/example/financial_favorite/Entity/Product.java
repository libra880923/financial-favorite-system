package com.example.financial_favorite.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "No")
    private Integer no;

    @Column(name = "ProductName", length = 100, nullable = false)
    private String productName;

    @Column(name = "Price", precision = 15, scale = 4, nullable = false)
    private BigDecimal price;

    @Column(name = "FeeRate", precision = 6, scale = 4, nullable = false)
    private BigDecimal feeRate;
}
