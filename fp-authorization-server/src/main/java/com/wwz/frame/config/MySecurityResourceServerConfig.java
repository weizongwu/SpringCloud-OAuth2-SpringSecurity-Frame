package com.wwz.frame.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 资源认证
 * @Author wwz
 * @Date 2019/07/30
 * @Param
 * @Return
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)   // 启用注解权限配置
@Order(3)
public class MySecurityResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Resource
    private MyAccessDeniedHandler accessDeniedHandler; // 无权访问处理器

    @Resource
    private MyTokenExceptionEntryPoint tokenExceptionEntryPoint; // token失效处理器

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .requestMatchers().antMatchers("/**") // .requestMatchers().antMatchers(...)，表示对资源进行保护，也就是说，在访问前要进行OAuth认证。
                .and()
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 另外，如果不设置，那么在通过浏览器访问被保护的任何资源时，每次是不同的SessionID，并且将每次请求的历史都记录在OAuth2Authentication的details的中
                .and()
                .httpBasic();
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(tokenExceptionEntryPoint); // token失效处理器
        resources.resourceId("auth"); // 设置资源id  通过client的 resource_ids 来判断是否具有资源权限
    }
}
