package com.wwz.frame.config;

import com.wwz.frame.common.HttpUtilsResultVO;
import com.wwz.frame.common.ResponseVo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 无权访问处理器
 * @Author wwz
 * @Date 2019/07/30
 * @Param
 * @Return
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * Handles an access denied failure.
     *
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException      in the event of an IOException
     * @throws ServletException in the event of a ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseVo resultVo = new ResponseVo();
        resultVo.setMessage("无权访问!");
        resultVo.setCode(403);
        HttpUtilsResultVO.writerError(resultVo, response);
    }
}
