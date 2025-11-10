package com.board.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // CORS 설정
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
          CorsConfiguration config = new CorsConfiguration();
          config.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
          config.setAllowedMethods(Collections.singletonList("*")); // 모든 HTTP 메서드 허용
          config.setAllowedOriginPatterns(Collections.singletonList("*")); // 모든 출처(Origin) 허용
          config.setAllowCredentials(true); // 쿠키나 인증정보 포함 요청 허용
          return  config;
        };
    }


    // 보안 필터 체인 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(CsrfConfigurer<HttpSecurity>::disable) //CSRF도 비활성화
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                // 요청 경로별 접근 제어 설정
                .authorizeHttpRequests(requests -> requests.requestMatchers(
                        "/_api/v1/**"
                        , "/"
                        , "/**"
                        , "/swagger-ui/**"
                        , "swagger-ui.html"
                        , "/swagger-ui**"
                        , "/v3/api-docs/**"
                ).permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
