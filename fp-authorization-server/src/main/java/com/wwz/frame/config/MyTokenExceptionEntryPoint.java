package com.wwz.frame.config;

import com.wwz.frame.common.HttpUtilsResultVO;
import com.wwz.frame.common.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 无效Token返回处理器
 * @Author wwz
 * @Date 2019/07/30
 * @Param
 * @Return
 */
@Component
public class MyTokenExceptionEntryPoint implements AuthenticationEntryPoint {
    /**
     * Commences an authentication scheme.
     * <p>
     * <code>ExceptionTranslationFilter</code> will populate the <code>HttpSession</code>
     * attribute named
     * <code>AbstractAuthenticationProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY</code>
     * with the requested target URL before calling this method.
     * <p>
     * Implementations should modify the headers on the <code>ServletResponse</code> as
     * necessary to commence the authentication process.
     *
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Throwable cause = authException.getCause();
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            if (cause instanceof InvalidTokenException) {
                HttpUtilsResultVO.writerError(new ResponseVo(405, "token失效"), response);
                //response.getWriter().write(JSONObject.toJSONString(new ResultVo(405, "token失效")));
            }else {
                HttpUtilsResultVO.writerError(new ResponseVo(405, "token缺失"), response);
//                response.getWriter().write(JSONObject.toJSONString(new ResultVo(405, "token缺失")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
