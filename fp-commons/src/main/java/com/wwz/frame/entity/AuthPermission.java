package com.wwz.frame.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 权限实体
 * @Author wwz
 * @Date 2019/07/26
 * @Param
 * @Return
 */
@Data
@ApiModel(value = "AuthPermission", description = "权限实体")
public class AuthPermission implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "自增id", name = "id", required = true)
    private Integer id;
    @ApiModelProperty(value = "权限名称", name = "permissionName", required = true)
    private String permissionName;
    @ApiModelProperty(value = "服务名", name = "servicePrefix", required = true)
    private String servicePrefix;
    @ApiModelProperty(value = "请求方式 restful 模式 GET获取 POST 新增 PUT更新 DELETE 删除", name = "method", required = true)
    private String method;
    @ApiModelProperty(value = "资源地址", name = "uri", required = true)
    private String uri;
    @ApiModelProperty(value = "是否可用", name = "valid", required = true)
    private Boolean valid;
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    @ApiModelProperty(value = "操作用户id", name = "operateId", required = true)
    private Integer operateId; // 操作用户id
}
