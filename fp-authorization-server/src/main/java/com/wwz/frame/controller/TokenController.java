package com.wwz.frame.controller;

import com.alibaba.fastjson.JSONObject;
import com.wwz.frame.common.LinkStringUtil;
import com.wwz.frame.common.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 登录登出
 * @Author wwz
 * @Date 2019/08/02
 * @Param
 * @Return
 */
@RestController
@RequestMapping("/client")
public class TokenController {
    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    RestTemplate restTemplate;

    @DeleteMapping("/logout")
    public ResponseVo logout(String accessToken) {
        if (consumerTokenServices.revokeToken(accessToken)) {
            return new ResponseVo(200, "登出成功");
        } else {
            return new ResponseVo(500, "登出失败");
        }
    }

    @PostMapping("/login")
    public ResponseVo login(HttpServletRequest request) throws UnsupportedEncodingException {
        String header = request.getHeader("Authorization");
        if (header == null && !header.startsWith("Basic")) {
            return new ResponseVo(400, "请求头中缺少参数");
        }
        String url = "http://" + request.getRemoteAddr() + ":" + request.getServerPort() + "/oauth/token";

        Map<String, Object> map = new HashMap<>();
        map.put("grant_type", "password");
        map.put("username", request.getParameter("username"));
        map.put("password", request.getParameter("password"));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", header);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);  // 必须该模式，不然请求端无法取到 grant_type

        HttpEntity httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url + "?" + LinkStringUtil.createLinkStringByGet(map), httpEntity, String.class);

        if(response.getStatusCodeValue()==200){
            return new ResponseVo(200, "登录成功", JSONObject.parseObject(response.getBody()));
        }else{
            return new ResponseVo(500,"登录失败");
        }
    }

}
