package com.board.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("게시판 REST API")
                .description("게시판 서비스의 글 작성, 조회, 수정, 삭제 기능 API 문서")
                .contact(new Contact().name("관리자").url("/").email(""))
                .license(new License().name("License of name").url("/"))
                .version("v1.0");

        String jwtSchemeName = "jwtAuth";

        // API 요청이 JWT 인증을 요구하도록 설정
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        // Swagger에 등록할 보안 스키마(JWT 인증 방식)을 정의
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName) // 스키마 이름
                        .type(SecurityScheme.Type.HTTP) // HTTP 기반 인증 방식 사용
                        .scheme("Bearer") // Bearer 인증
                        .bearerFormat("JWT") // 형식 지정 (JWT)
                );



        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }

}
