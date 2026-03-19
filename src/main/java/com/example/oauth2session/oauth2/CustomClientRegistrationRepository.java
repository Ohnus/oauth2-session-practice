package com.example.oauth2session.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
@RequiredArgsConstructor
public class CustomClientRegistrationRepository {

    private final SocialClientRegistration socialClientRegistration;

    public ClientRegistrationRepository getClientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                socialClientRegistration.getNaverClientRegistration(),
                socialClientRegistration.getGoogleClientRegistration());
    }
}
