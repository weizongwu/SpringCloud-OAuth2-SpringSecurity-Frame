package com.wwz.frame.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description FeignClient 请求配置器，增加token信息
 * @Author wwz
 * @Date 2019/11/20
 * @Email wwzwtf@qq.com
 * @Param
 * @Return
 */
@Configuration
public class FeignConfig implements RequestInterceptor {

    public static String TOKEN_HEADER = "authorization";

    @Override
    public void apply(RequestTemplate template) {
        template.header(TOKEN_HEADER, getHeaders(getHttpServletRequest()).get(TOKEN_HEADER));
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
