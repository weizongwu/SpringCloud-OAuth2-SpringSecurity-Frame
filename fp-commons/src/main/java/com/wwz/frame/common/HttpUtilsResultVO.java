package com.wwz.frame.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @Description 异常直接返回工具类
* @Author wwz
* @Date 2019/07/10
* @Param
* @Return
*/
public class HttpUtilsResultVO {

    public static void writerError(ResponseVo r, HttpServletResponse response) throws IOException {
        response.setContentType("application/json,charset=utf-8");
        response.setStatus(r.getCode());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), r);
    }

}
