package com.wwz.frame.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 资源认证
 * @Author wwz
 * @Date 2019/08/01
 * @Param
 * @Return
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)   // 启用注解权限配置
public class MySecurityResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore redis = new RedisTokenStore(connectionFactory);
        return redis;
    }

    @Resource
    private MyAccessDeniedHandler accessDeniedHandler; // 无权访问处理器

    @Resource
    private MyTokenExceptionEntryPoint tokenExceptionEntryPoint; // token失效处理器

    @Resource
    private MySecurityAccessDecisionManager accessDecisionManager; //权限判断

    @Resource
    private MyFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource; // 请求需要权限

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 另外，如果不设置，那么在通过浏览器访问被保护的任何资源时，每次是不同的SessionID，并且将每次请求的历史都记录在OAuth2Authentication的details的中
                .and()
                .authorizeRequests().antMatchers("/actuator/health").permitAll().anyRequest().authenticated()  // httpSecurity 放过健康检查，其他都需要验证  设置了.anyRequest().authenticated()才回进入自定义的权限判断
                .and()
                .requestMatchers().antMatchers("/auth/**") // .requestMatchers().antMatchers(...) OAuth2设置对资源的保护如果是用 /**的话 会把上面的也拦截掉
                .and()
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {       // 重写做权限判断
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource); // 请求需要权限
                        o.setAccessDecisionManager(accessDecisionManager);      // 权限判断
                        return o;
                    }
                })
                .and()
                .httpBasic();

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(tokenExceptionEntryPoint); // token失效处理器
        resources.resourceId("manager"); // 设置资源id  通过client的 resource_ids 来判断是否具有资源权限  资源不存在会报Invalid token does not contain resource id (manager)
    }
}
