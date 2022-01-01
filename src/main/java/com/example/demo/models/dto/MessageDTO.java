package com.example.demo.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDTO<T> {
    private int status;

    private String error;

    private T data;

    public MessageDTO(int status, String message) {
        this.status = status;
        this.error = message;
    }

    public MessageDTO(int status, String message, T data) {
        this.status = status;
        this.error = message;
        this.data = data;
    }
}
