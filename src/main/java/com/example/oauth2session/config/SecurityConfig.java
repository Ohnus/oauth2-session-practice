package com.example.oauth2session.config;

import com.example.oauth2session.oauth2.CustomClientRegistrationRepository;
import com.example.oauth2session.oauth2.CustomOAuth2AuthorizedClientService;
import com.example.oauth2session.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomClientRegistrationRepository customClientRegistrationRepository;
    private final CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService;
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.formLogin(login -> login.disable());

        http.httpBasic(basic -> basic.disable());

        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .clientRegistrationRepository(customClientRegistrationRepository.getClientRegistrationRepository())
                .authorizedClientService(customOAuth2AuthorizedClientService.getOAuth2AuthorizedClientService(
                        jdbcTemplate, customClientRegistrationRepository.getClientRegistrationRepository()))
                .userInfoEndpoint(userInfoEndpointConfig ->
                        userInfoEndpointConfig.userService(customOAuth2UserService)));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
                .anyRequest().authenticated());

        return http.build();
    }
}
