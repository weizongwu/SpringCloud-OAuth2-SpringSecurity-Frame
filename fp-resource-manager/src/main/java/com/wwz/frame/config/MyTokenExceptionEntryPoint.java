package com.wwz.frame.config;

import com.wwz.frame.common.HttpUtilsResultVO;
import com.wwz.frame.common.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 无效Token返回处理器
 * @Author wwz
 * @Date 2019/08/01
 * @Param
 * @Return
 */
@Component
public class MyTokenExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Throwable cause = authException.getCause();
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
                HttpUtilsResultVO.writerError(new ResponseVo(401, authException.getMessage()), response);
                //response.getWriter().write(JSONObject.toJSONString(new ResultVo(405, "token失效")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
