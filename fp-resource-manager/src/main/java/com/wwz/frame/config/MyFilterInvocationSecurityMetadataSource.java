package com.wwz.frame.config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 根据url获取 url需要访问的权限
 * @Author wwz
 * @Date 2019/08/01
 * @Param
 * @Return
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private AntPathMatcher antPathMatcher = new AntPathMatcher(); // 模糊匹配 如何 auth/**   auth/auth

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Set<ConfigAttribute> set = new HashSet<>();

        String requestUrl = ((FilterInvocation) object).getRequest().getMethod() + ((FilterInvocation) object).getRequest().getRequestURI();
        System.out.println("requestUrl >> " + requestUrl);

        // 这里获取对比数据可以从数据库或者内存 redis等等地方获取 目前先写死后面优化
        String url = "GET/auth/**";
        if (antPathMatcher.match(url, requestUrl)) {
            SecurityConfig securityConfig = new SecurityConfig("ROLE_ADMIN");
            set.add(securityConfig);
        }
        if (ObjectUtils.isEmpty(set)) {
            return SecurityConfig.createList("ROLE_LOGIN");
        }
        return set;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
