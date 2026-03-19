package com.example.oauth2session.oauth2.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

public class NaverResponseDto implements OAuth2ResponseDto {

    // 로그인한 사용자 정보 받기 위해 생성자 주입
    private final Map<String, Object> attributes;

    // 네이버는 response 안에 데이터가 있는 형태
    public NaverResponseDto(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    // 네이버에서 제공하는 사용자 고유 아이디
    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    // 사용자 이메일
    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    // 사용자 이름
    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    // 사용자 성별
    public String getGender() {
        return attributes.get("gender").toString();
    }

    // 사용자 핸드폰번호
    public String getMobile() {
        return attributes.get("mobile").toString();
    }
}
