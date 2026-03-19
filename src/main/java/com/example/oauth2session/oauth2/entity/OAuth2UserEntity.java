package com.example.oauth2session.oauth2.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_oauth")
public class OAuth2UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String provider;
    private String providerId;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private OAuth2UserEnum role;

    @Builder
    public OAuth2UserEntity(String provider, String providerId, String email, OAuth2UserEnum role) {
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.role = role;
    }

    public void modifyEmail(String email) {
        this.email = email;
    }

}
