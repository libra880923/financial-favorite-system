package com.example.financial_favorite.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "LikeList")
public class LikeList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SN")
    private Integer sn;

    @Column(name = "UserID", length = 20, nullable = false)
    private String userID;

    @Column(name = "ProductNo", nullable = false)
    private Integer productNo;

    @Column(name = "PurchaseQuantity", nullable = false)
    private Integer purchaseQuantity;

    @Column(name = "Account", length = 20, nullable = false)
    private String account;

    @Column(name = "TotalFee", precision = 15, scale = 4, nullable = false)
    private BigDecimal totalFee;

    @Column(name = "TotalAmount", precision = 15, scale = 4, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "CreateTime", updatable = false)
    private LocalDateTime createTime;

    @Column(name = "UpdateTime")
    private LocalDateTime updateTime;
}
