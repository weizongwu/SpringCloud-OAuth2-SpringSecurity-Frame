package com.wwz.frame.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

/**
 * @Description Swagger api 配置  模式二：增加登录
 * @Author wwz
 * @Date 2019/08/05
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Value("${swagger.is.enable}")
    private boolean SWAGGER_IS_ENABLE; //是否激活开关，在application.yml中配置注入
    @Value("${swagger.auth.server}")
    private String AUTH_SERVER;
    @Value("${swagger.service.name}")
    private String SERVICE_NAME;
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(SWAGGER_IS_ENABLE)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wwz.frame.controller"))
                .paths(PathSelectors.any())
                .build()
//                .pathMapping(SERVICE_NAME)
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("OAuth2权限管理API文档")
                .contact(new Contact("wwz", "", "wwzwtf@qq.com"))
                .description("OAuth2维护文档")
                .version("1.0")
                .extensions(Collections.emptyList())
                .build();
    }

    /**
     * 这个类决定了你使用哪种认证方式，我这里使用密码模式
     */
    private SecurityScheme securityScheme() {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(AUTH_SERVER);

        return new OAuthBuilder()
                .name("OAuth2")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    /**
     * 这里设置 swagger2 认证的安全上下文
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("OAuth2", scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }

    /**
     * 这里是写允许认证的scope
     */
    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
        };
    }
}
