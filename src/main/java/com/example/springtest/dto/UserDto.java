package com.example.springtest.dto;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String name;
    private String pw;
    private String role; // USER 또는 ADMIN
}
