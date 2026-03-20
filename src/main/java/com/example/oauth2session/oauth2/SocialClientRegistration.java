package com.example.oauth2session.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

// 제공자의 서버와 연결할 변수 설정 클래스
@Component
public class SocialClientRegistration {

    @Value("${oauth2.client.naver.id}")
    private String naverClientId;
    @Value("${oauth2.client.naver.secret}")
    private String naverClientSecret;
    @Value("${oauth2.client.google.id}")
    private String googleClientId;
    @Value("${oauth2.client.google.secret}")
    private String googleClientSecret;

    public ClientRegistration getNaverClientRegistration() {
        return ClientRegistration.withRegistrationId("naver")
                .clientId(naverClientId)
                .clientSecret(naverClientSecret)
                .redirectUri("http://localhost:8080/login/oauth2/code/naver")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("name", "email")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .build();
    }

    public ClientRegistration getGoogleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .redirectUri("http://localhost:8080/login/oauth2/code/google")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .issuerUri("https://accounts.google.com")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .build();
    }
}
