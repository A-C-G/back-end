package com.project.ACG.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {
    @Server(url = "/", description = "Default Server URL")
})
public class SwaggerConfig {

  // 공개 API를 그룹화하여 설정
  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("v1-definition")
        .pathsToMatch("/user/**") // "/user/**" 경로에 매핑된 API 그룹
        .build();
  }

  // SpringShopOpenAPI를 정의
  @Bean
  public OpenAPI springShopOpenAPI() {
    // 보안 스키마 설정
    SecurityScheme securityScheme = new SecurityScheme()
        .type(Type.APIKEY)
        .description("Api 키")
        .in(In.HEADER)
        .name("x-api-key");

    // 보안 요구사항 설정
    SecurityRequirement securityRequirement = new SecurityRequirement()
        .addList("api key");

    // OpenAPI 정보 설정
    return new OpenAPI()
        .components(new Components().addSecuritySchemes("api key", securityScheme))
        .addSecurityItem(securityRequirement)
        .info(new Info().title("ACG project") // 프로젝트 제목
            .contact(new Contact().name("ACG").email("hyuntae9912@naver.com")) // 연락처 정보
            .description("ACG API 명세서") // API 설명
            .summary("ACG API입니다.") // API 요약
            .version("v0.0.1")); // API 버전
  }
}
