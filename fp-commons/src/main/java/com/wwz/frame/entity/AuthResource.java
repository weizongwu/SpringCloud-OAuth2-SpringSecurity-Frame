package com.wwz.frame.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 资源权限配置实体
 * @Author wwz
 * @Date 2019/07/26
 * @Param
 * @Return
 */
@Data
@ApiModel(value = "AuthResource", description = "资源权限配置实体")
public class AuthResource implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "自增id", name = "id",required = true)
    private Integer id;
    @ApiModelProperty(value = " serviceName|Method|URI  资源名称|请求方式|URI (资源地址)", name = "url", required = true)
    private String url;
    @ApiModelProperty(value = "资源需要权限 多个逗号隔开，且用#隔开 如 ROLE_ADMIN，资源名称|URI|Method  (restFul标识)',th/auth/getAuthUser/GET,ROLE_ADMIN  需要GET权限或ROLE_ADMIN角色", name = "needPermission", required = true)
    private String needPermission;
    @ApiModelProperty(hidden = true)
    private Date createTime;
    @ApiModelProperty(hidden = true)
    private Date updateTime;
    @ApiModelProperty(value = "操作用户id", name = "operateId", required = true)
    private Integer operateId;
}
