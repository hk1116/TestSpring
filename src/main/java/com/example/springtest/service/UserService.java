package com.example.springtest.service;

import com.example.springtest.domain.User;
import com.example.springtest.dto.LoginRequest;
import com.example.springtest.dto.UserDto;
import com.example.springtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // 회원가입 처리
    public User registerUser(UserDto userDto) {
        String role = userDto.getRole() != null ? userDto.getRole() : "ROLE_USER";

        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .pw(passwordEncoder.encode(userDto.getPw())) // 비밀번호 암호화
                .role(role)
                .build();

        return userRepository.save(user);
    }

    // 로그인 처리 및 JWT 토큰 발급
    public String loginUser(LoginRequest loginRequest) {
        // 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPw())
        );

        // 사용자를 데이터베이스에서 찾기
        User user = userRepository.findById(loginRequest.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + loginRequest.getId()));

        // JWT 토큰 생성
        return jwtTokenUtil.generateToken(user.getId(), user.getRole(), user.getName());
    }

//    회원정보수정
    public void updateUser(String id, UserDto userDto) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDto.getName());
            if (userDto.getPw() != null && !userDto.getPw().isEmpty()) {
                user.setPw(passwordEncoder.encode(userDto.getPw())); // 비밀번호 변경 시 인코딩
            }
            userRepository.save(user);
        } else {
            throw new Exception("User not found");
        }
    }

    // 새로운 getUserDetails 메서드 추가
    public UserDto getUserDetails(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setPw(""); // 비밀번호는 반환하지 않음
            userDto.setRole(user.getRole());
            return userDto;
        }
        return null;
    }
}
