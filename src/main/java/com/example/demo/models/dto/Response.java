package com.example.demo.models.dto;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response{

    private Response() {

    }

    public static <T> MessageDTO<T> from(HttpStatus status, String msg) {
        return new MessageDTO<>(status.value(), msg);
    }

    public static <T> ResponseEntity<MessageDTO<T>> unauthorized(String msg) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageDTO<>(401, msg));
    }

    public static <T> ResponseEntity<MessageDTO<T>> unauthorized(int code, String msg) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageDTO<>(code, msg));
    }

    public static <T> ResponseEntity<MessageDTO<T>>badRequest(String msg) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageDTO<>(400, msg));
    }

    public static <T> ResponseEntity<MessageDTO<T>> badRequest(int code, String msg) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageDTO<>(code, msg));
    }

    public static <T> ResponseEntity<MessageDTO<T>> badRequest(int code, String msg, T data) {
        return ResponseEntity
                .badRequest()
                .body(new MessageDTO<T>(code, msg, data));
    }

    public static <T> ResponseEntity<MessageDTO<T>> ok(int code, String msg, T body) {
        return ResponseEntity.ok(new MessageDTO<>(code, msg, body));
    }

    public static <T> ResponseEntity<MessageDTO<T>> ok(T body) {
        return ResponseEntity.ok(new MessageDTO<>(200, "SUCCESS", body));
    }

    public static <T> ResponseEntity<MessageDTO<T>> ok() {
        return ResponseEntity.ok(new MessageDTO<>(200, "SUCCESS", null));
    }

    public static <T> ResponseEntity<MessageDTO<T>> ok(int code, String message) {
        return ResponseEntity.ok(new MessageDTO<>(code, message));
    }

    public static <T> ResponseEntity<MessageDTO<T>> ok(String message, T body) {
        return ResponseEntity.ok(new MessageDTO<>(200, message, body));
    }

//    public static <T> ResponseEntity<> httpError(HttpErrorException e) {
//        return ResponseEntity
//                .status(e.getStatus())
//                .body(from(e.getStatus(), e.getMessage()));
//    }

}
