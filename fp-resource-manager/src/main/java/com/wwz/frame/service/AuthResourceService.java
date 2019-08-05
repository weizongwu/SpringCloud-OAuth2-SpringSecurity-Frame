package com.wwz.frame.service;

import com.wwz.frame.common.ResponseVo;
import com.wwz.frame.entity.AuthResource;

import java.util.List;


public interface AuthResourceService {
    /**
     * 获取全部
     */
    List<AuthResource> listAuthResource();

    /**
     * 新建 资源权限
     */
    ResponseVo saveAuthResource(AuthResource authResource);

    /**
     * 更新 资源权限
     */
    ResponseVo updateAuthResource(AuthResource authResource);

    /**
     * 删除 资源权限
     */
    ResponseVo removeAuthResource(Integer id);

}
