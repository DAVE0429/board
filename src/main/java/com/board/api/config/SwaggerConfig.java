package com.board.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
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

        return new OpenAPI()
                .info(info);
    }

}
