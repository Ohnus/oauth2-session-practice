package com.example.oauth2session.oauth2.service;

import com.example.oauth2session.oauth2.dto.CustomOAuth2User;
import com.example.oauth2session.oauth2.dto.GoogleResponseDto;
import com.example.oauth2session.oauth2.dto.NaverResponseDto;
import com.example.oauth2session.oauth2.dto.OAuth2ResponseDto;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모 클래스에 존재하는 loadUser 메서드를 통해 유저 정보 획득
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        // OAuth 제공자 아이디 획득
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 제공자별로 분기하여 각 dto에 속성 넘겨주기
        OAuth2ResponseDto oAuth2ResponseDto = null;
        if (registrationId.equals("naver")) {
            oAuth2ResponseDto = new NaverResponseDto(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2ResponseDto = new GoogleResponseDto(oAuth2User.getAttributes());
        } else {
            return null;
        }


        return new CustomOAuth2User(oAuth2ResponseDto, "ROLE_USER");
    }
}
