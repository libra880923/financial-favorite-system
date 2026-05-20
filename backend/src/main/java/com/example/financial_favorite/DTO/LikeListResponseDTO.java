package com.example.financial_favorite.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LikeListResponseDTO {
    private Integer sn;
    private String productName;
    private BigDecimal price;
    private BigDecimal feeRate;
    private Integer purchaseQuantity;
    private String account;
    private BigDecimal totalFee;
    private BigDecimal totalAmount;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
