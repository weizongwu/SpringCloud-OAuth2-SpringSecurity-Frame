package com.wwz.frame.config;

import com.wwz.frame.common.HttpUtilsResultVO;
import com.wwz.frame.common.ResponseVo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 登录失败处理器
 * @Author wwz
 * @Date 2019/08/05
 * @Param
 * @Return
 */
@Component
public class MyLoginAuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(401);
        responseVo.setMessage(exception.getMessage());
        responseVo.setData("path:"+request.getRequestURI());
        response.setStatus(401);
        HttpUtilsResultVO.writerError(responseVo, response);
    }
}
