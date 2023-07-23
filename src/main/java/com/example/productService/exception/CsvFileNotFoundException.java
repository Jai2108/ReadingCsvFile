package com.example.productService.exception;

public class CsvFileNotFoundException  extends RuntimeException {
    public CsvFileNotFoundException(String message) {
        super(message);
    }
}