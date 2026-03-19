package com.example.oauth2session.oauth2.repository;

import com.example.oauth2session.oauth2.entity.OAuth2UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuth2UserRepository extends JpaRepository<OAuth2UserEntity, Long> {

    Optional<OAuth2UserEntity> findByProviderAndProviderId(String provider, String providerId);
}
