//package com.wwz.frame.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
///**
// * @Description Swagger api 配置    模式一：  增加Authorization 窗口传token
// * @Author wwz
// * @Date 2019/08/05
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig2 {
//
//    @Value("${swagger.is.enable}")
//    private boolean SWAGGER_IS_ENABLE; //是否激活开关，在application.yml中配置注入
//
//    @Bean
//    public Docket docket() {
//        //添加head参数配置start
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());
//        return new Docket(DocumentationType.SWAGGER_2)
//                .enable(SWAGGER_IS_ENABLE)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.wwz.frame.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .globalOperationParameters(pars);//注意这里;
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                // 页面标题
//                .title("OAuth2权限管理API文档")
//                .contact(new Contact("wwz", "", "wwzwtf@qq.com"))
//                .description("OAuth2维护文档")
//                .version("1.0")
//                .extensions(Collections.emptyList())
//                .build();
//    }
//}