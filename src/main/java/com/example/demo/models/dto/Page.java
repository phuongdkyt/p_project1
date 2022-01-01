package com.example.demo.models.dto;

import lombok.Data;

@Data
public class Page<T> {
    private int size;
    private int page;
    private int totalPage;
    private long totalRecord;
    private int totalInPage;
    private T data;
}
