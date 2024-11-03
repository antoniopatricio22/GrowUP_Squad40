
package com.squad40.compesa.model;

public class StandardResponse<T> {
    private String status;
    private String message;
    private T data;
    private String error;

    public StandardResponse(String status, String message, T data, String error) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public static <T> StandardResponse<T> success(String message, T data) {
        return new StandardResponse<>("success", message, data, null);
    }

    public static <T> StandardResponse<T> error(String message, String error) {
        return new StandardResponse<>("error", message, null, error);
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
