package com.example.springtest.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Getter, Setter, ToString, EqualsAndHashCode, RequiredArgsConstructor를 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 자동 생성
@Builder // 빌더 패턴 메서드 자동 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가되는 기본 키로 설정
    private Long idx;

    @Column(unique = true, nullable = false) // 고유하고 null이 될 수 없는 문자열 필드
    private String id;
    private String name;
    private String pw;

    @Column(nullable = false)
    private String role; // 역할 필드 추가
}
