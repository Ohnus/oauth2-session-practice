package com.example.oauth2session.oauth2.dto;

public interface OAuth2ResponseDto {

    // 제공자
    String getProvider();
    // 고유 식별 아이디
    String getProviderId();
    // 이메일
    String getEmail();
    // 사용자 이름
    String getName();
}
