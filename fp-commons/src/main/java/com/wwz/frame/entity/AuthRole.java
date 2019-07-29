package com.wwz.frame.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Description 角色实体
 * @Author wwz
 * @Date 2019/07/26
 * @Param
 * @Return
 */
@Data
@ApiModel(value = "AuthRole", description = "角色实体")
public class AuthRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "自增id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "角色名称 必须ROLE_开头", name = "roleName", required = true)
    private String roleName;
    @ApiModelProperty(value = "角色中文名", name = "roleChName", required = true)
    private String roleChName;
    @ApiModelProperty(value = "是否可用", name = "valid", required = true)
    private Boolean valid;
    @ApiModelProperty(hidden = true)
    private Date createTime;
    @ApiModelProperty(hidden = true)
    private Date updateTime;
    @ApiModelProperty(value = "操作用户id", name = "operateId", required = true)
    private Integer operateId;
    @ApiModelProperty(hidden = true)
    private Set<AuthPermission> authPermissions;
}
