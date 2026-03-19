package com.example.oauth2session.oauth2.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final OAuth2ResponseDto oAuth2ResponseDto;
    private final String role;

    // 넘어오는 모든 데이터
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    // 권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(role)
        );
    }

    // 사용자의 이름 또는 별명
    @Override
    public String getName() {
        return oAuth2ResponseDto.getName();
    }

    // 유저 아이디 커스텀 생성(프로바이더_고유아이디)
    public String getUsername() {
        return oAuth2ResponseDto.getProvider() + "_" + oAuth2ResponseDto.getProviderId();
    }
}
