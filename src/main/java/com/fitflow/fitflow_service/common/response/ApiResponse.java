package com.fitflow.fitflow_service.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private int statusCode;

    public static <T> ApiResponse<T> success(String message, T data, int statusCode) {
        return new ApiResponse<>(true, message, data, statusCode);
    }

    public static <T> ApiResponse<T> error(String message, T data, int statusCode) {
        return new ApiResponse<>(false, message, data, statusCode);
    }

    public static <T> ApiResponse<T> success(String message, int statusCode) {
        return new ApiResponse<>(true, message, null, statusCode);
    }

    public static <T> ApiResponse<T> error(String message, int statusCode) {
        return new ApiResponse<>(false, message, null, statusCode);
    }
}
