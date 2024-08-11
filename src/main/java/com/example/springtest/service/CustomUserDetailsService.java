package com.example.springtest.service;

import com.example.springtest.domain.User;
import com.example.springtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // `id`로 사용자를 조회하도록 수정
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        // 사용자의 역할을 포함하여 UserDetails 객체를 생성
        return new org.springframework.security.core.userdetails.User(
                user.getId(),
                user.getPw(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
