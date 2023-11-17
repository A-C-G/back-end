package com.project.ACG.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	protected SecurityFilterChain config(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/gs-guide-websocket/**").permitAll() // WebSocket에 대한 접근 허용
			.anyRequest().authenticated()
			.and()
			.oauth2Login()
			.authorizationEndpoint()
			.baseUri("/login");
		http.csrf().disable();
		return http.build();
	}
}

