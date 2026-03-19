package com.example.oauth2session.oauth2.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleResponseDto implements OAuth2ResponseDto {

    private final Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "google";
    }

    // 구글에서 제공하는 사용자 고유 아이디
    @Override
    public String getProviderId() {
        return attributes.get("sub").toString();
    }

    // 이메일
    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    // 이름
    @Override
    public String getName() {
        return attributes.get("name").toString();
    }
}
