package com.project.ACG.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // OAuth2 로그인 구성을 위한 보안 필터 체인 설정
  @Bean
  protected SecurityFilterChain config(HttpSecurity http) throws Exception {
    http.oauth2Login()
        .authorizationEndpoint()
        .baseUri("/login"); // 인증 엔드포인트를 "/login"으로 설정
    http.csrf().disable(); // CSRF 보호 비활성화
    return http.build(); // 보안 필터 체인 구성 반환
  }
}

