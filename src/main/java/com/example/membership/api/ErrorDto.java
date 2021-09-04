package com.example.membership.api;

import lombok.Getter;

@Getter
public class ErrorDto {
    private String message;
    private int status;

    public ErrorDto(RuntimeException error) {
        this.message = error.getMessage();
        status = 400;
    }
}
