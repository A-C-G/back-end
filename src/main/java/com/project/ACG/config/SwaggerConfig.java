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

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("v1-definition")
        .pathsToMatch("/user/**")
        .build();
  }

  @Bean
  public OpenAPI springShopOpenAPI() {
    SecurityScheme securityScheme = new SecurityScheme()
        .type(Type.APIKEY)
        .description("Api 키")
        .in(In.HEADER)
        .name("x-api-key");

    SecurityRequirement securityRequirement = new SecurityRequirement()
        .addList("api key");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("api key", securityScheme))
        .addSecurityItem(securityRequirement)
        .info(new Info().title("ACG project")
            .contact(new Contact().name("ACG").email("hyuntae9912@naver.com"))
            .description("ACG API 명세서")
            .summary("ACG API입니다.")
            .version("v0.0.1"));
  }
}
