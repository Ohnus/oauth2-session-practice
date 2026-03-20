package com.example.oauth2session.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

// 제공자의 인증 서버로부터 받은 토큰 등 정보를 담을 서비스 클래스
@Configuration
public class CustomOAuth2AuthorizedClientService {

    public OAuth2AuthorizedClientService getOAuth2AuthorizedClientService(
            JdbcTemplate jdbcTemplate, ClientRegistrationRepository clientRegistrationRepository) {

        // 기존에 인메모리 반환 방식이었던 것을 JDBC로 수정
        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate, clientRegistrationRepository);
    }
}
