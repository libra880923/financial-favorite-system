package com.example.financial_favorite.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LikeListRequestDTO {
    @NotBlank(message = "使用者ID不可為空")
    @Size(max = 20, message = "使用者ID長度不可超過20")
    private String userID;

    @NotNull(message = "產品編號不可為空")
    @Min(value = 1, message = "產品編號須為正整數")
    private Integer productNo;

    @NotNull(message = "購買數量不可為空")
    @Min(value = 1, message = "購買數量最少為1")
    private Integer purchaseQuantity;

    @NotBlank(message = "扣款帳號不可為空")
    @Pattern(regexp = "^[0-9]{10}$", message = "扣款帳號需為10位數字")
    private String account;
}
