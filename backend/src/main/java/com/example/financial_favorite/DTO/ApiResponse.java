package com.example.financial_favorite.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;        // 0=成功, -1=失敗
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(0, "success", data);
    }
    public static <T> ApiResponse<T> error(String msg) {
        return new ApiResponse<>(-1, msg, null);
    }
}
