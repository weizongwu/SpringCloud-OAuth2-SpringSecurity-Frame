package com.wwz.frame.config;

import com.wwz.frame.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description security 配置
 * ResourceServerConfigurerAdapter 是比WebSecurityConfigurerAdapter 的优先级低的
 * @Author wwz
 * @Date 2019/07/28
 * @Param
 * @Return
 */
@Configuration
@EnableWebSecurity
@Order(2)  // WebSecurityConfigurerAdapter 默认为100 这里配置为2设置比资源认证器高
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    // 自定义用户验证数据
    @Bean
    public MyUserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    // 加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 验证器加载
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 匹配oauth相关，匹配健康，匹配默认登录登出 在httpSecurity处理，，其他到ResourceServerConfigurerAdapter OAuth2处理  1
                .requestMatchers().antMatchers("/oauth/**", "/actuator/health", "/client/**")
                .and()
                // 匹配的全部无条件通过 permitAll 2
                .authorizeRequests().antMatchers("/oauth/**", "/actuator/health", "/client/**").permitAll()
                // 匹配条件1的 并且不再条件2通过范围内的其他url全部需要验证登录
                .and().authorizeRequests().anyRequest().authenticated()
                // 启用登录验证
                .and().formLogin().permitAll();
        // 不启用 跨站请求伪造 默认为启用， 需要启用的话得在form表单中生成一个_csrf
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }
}
