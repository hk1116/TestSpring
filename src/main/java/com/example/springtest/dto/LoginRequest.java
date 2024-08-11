package com.example.springtest.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String id;
    private String pw;
}