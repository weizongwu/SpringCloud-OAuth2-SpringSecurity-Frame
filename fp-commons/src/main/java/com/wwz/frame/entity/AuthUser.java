package com.wwz.frame.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Description 用户实体
 * @Author wwz
 * @Date 2019/07/26
 * @Param
 * @Return
 */
@Data
@ApiModel(value = "AuthUser", description = "用户实体")
public class AuthUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "自增id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "用户名", name = "username", required = true)
    private String username;
    @ApiModelProperty(value = "密码", name = "password", required = true)
    private String password;
    @ApiModelProperty(value = "中文名", name = "chName", required = true)
    private String chName;
    @ApiModelProperty(value = "可用性", name = "valid", required = true)
    private Boolean valid;
    @ApiModelProperty(value = "过期性 true 没过期 false 过期", name = "accountNonExpired", required = true)
    private Boolean accountNonExpired;
    @ApiModelProperty(value = "有效性", name = "credentialsNonExpired", required = true)
    private Boolean credentialsNonExpired;
    @ApiModelProperty(value = "锁定性 1未锁定 0锁定", name = "accountNonLocked", required = true)
    private Boolean accountNonLocked;
    @ApiModelProperty(hidden = true)
    private Date createTime;
    @ApiModelProperty(hidden = true)
    private Date updateTime;
    @ApiModelProperty(value = "个人说明", name = "description")
    private String description;
    @ApiModelProperty(value = "操作用户id", name = "operateId", required = true)
    private Integer operateId;
    @ApiModelProperty(hidden = true)
    private Set<AuthRole> authRoles;
}
