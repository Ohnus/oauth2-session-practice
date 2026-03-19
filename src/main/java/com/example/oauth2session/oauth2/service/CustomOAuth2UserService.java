package com.example.oauth2session.oauth2.service;

import com.example.oauth2session.oauth2.dto.CustomOAuth2User;
import com.example.oauth2session.oauth2.dto.GoogleResponseDto;
import com.example.oauth2session.oauth2.dto.NaverResponseDto;
import com.example.oauth2session.oauth2.dto.OAuth2ResponseDto;
import com.example.oauth2session.oauth2.entity.OAuth2UserEntity;
import com.example.oauth2session.oauth2.entity.OAuth2UserEnum;
import com.example.oauth2session.oauth2.repository.OAuth2UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuth2UserRepository oAuth2UserRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모 클래스에 존재하는 loadUser 메서드를 통해 유저 정보 획득
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        // OAuth 제공자 아이디 획득
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 제공자별로 분기하여 각 dto에 속성 넘겨주기
        OAuth2ResponseDto oAuth2ResponseDto = null;
        if (provider.equals("naver")) {
            System.out.println("naver");
            oAuth2ResponseDto = new NaverResponseDto(oAuth2User.getAttributes());
        } else if (provider.equals("google")) {
            System.out.println("google");
            oAuth2ResponseDto = new GoogleResponseDto(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // provider + providerUserId로 유무 확인
        String providerUserId = oAuth2ResponseDto.getProviderId();
        Optional<OAuth2UserEntity> account = oAuth2UserRepository.findByProviderAndProviderId(provider, providerUserId);

        OAuth2UserEntity oAuth2UserEntity;
        String email = oAuth2ResponseDto.getEmail();
        // 있을 경우 이메일 최신화, 없을 경우 새로 가입
        if(account.isPresent()) {
            oAuth2UserEntity = account.get();
            oAuth2UserEntity.modifyEmail(email);

            oAuth2UserRepository.save(oAuth2UserEntity);
        } else {
            oAuth2UserEntity = OAuth2UserEntity.builder()
                    .provider(provider)
                    .providerId(providerUserId)
                    .email(email)
                    .role(OAuth2UserEnum.ROLE_USER)
                    .build();

            oAuth2UserRepository.save(oAuth2UserEntity);
        }

        // 시큐리티에 넣을 객체 반환
        return new CustomOAuth2User(oAuth2ResponseDto, "ROLE_USER");
    }
}
