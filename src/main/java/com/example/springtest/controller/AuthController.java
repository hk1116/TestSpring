package com.example.springtest.controller;

import com.example.springtest.domain.User;
import com.example.springtest.dto.LoginRequest;
import com.example.springtest.dto.LoginResponse;
import com.example.springtest.dto.UserDto;
import com.example.springtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // 회원가입 엔드포인트
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        User registeredUser = userService.registerUser(userDto);
        return ResponseEntity.ok(registeredUser);
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = userService.loginUser(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token));    }

//    회원정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserDto userDto) {
        try {
            userService.updateUser(userDetails.getUsername(), userDto);
            return ResponseEntity.ok("회원정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("회원정보 수정에 실패했습니다.");
        }
    }

//    사용자 정보 가져오기
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        UserDto userDto = userService.getUserDetails(userDetails.getUsername());
        return ResponseEntity.ok(userDto);
    }
}