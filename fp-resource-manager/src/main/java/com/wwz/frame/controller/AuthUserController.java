package com.wwz.frame.controller;

import com.wwz.frame.common.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "AuthUserController", tags = "用户信息配置")
@RestController
@RequestMapping("/authUser")
public class AuthUserController {

    @ApiOperation("获取全部用户信息")
    @GetMapping
    public ResponseVo getAllAuthUser() {
        return new ResponseVo(200, "资源查询成功");
    }
}
