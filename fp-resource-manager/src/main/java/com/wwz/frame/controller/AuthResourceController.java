package com.wwz.frame.controller;

import com.wwz.frame.common.ResponseVo;
import com.wwz.frame.entity.AuthResource;
import com.wwz.frame.service.AuthResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "AuthResourceController", tags = "资源权限配置")
@RestController
@RequestMapping("/authResource")
public class AuthResourceController {

    @Autowired
    private AuthResourceService authResourceService;

    @ApiOperation("获取全部资源权限配置信息")
    @GetMapping
    public ResponseVo getAllAuthResources() {
        List<AuthResource> list = authResourceService.listAuthResource();
        return new ResponseVo(200, "资源查询成功", list);
    }

    @ApiOperation("新建资源配置")
    @PostMapping
    public ResponseVo saveAuthResource(@RequestBody AuthResource authResource) {
        return authResourceService.saveAuthResource(authResource);
    }

    @ApiOperation("更新资源配置")
    @PutMapping
    public ResponseVo updateAuthResource(@RequestBody AuthResource authResource) {
        return authResourceService.updateAuthResource(authResource);
    }

    @ApiOperation("删除资源配置")
    @DeleteMapping
    @ApiImplicitParam(name = "id", required = true, value = "资源配置id")
    public ResponseVo removeAuthResource(Integer id) {
        return authResourceService.removeAuthResource(id);
    }
}
