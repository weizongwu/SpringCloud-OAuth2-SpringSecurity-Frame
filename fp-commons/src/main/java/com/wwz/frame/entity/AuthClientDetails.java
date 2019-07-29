package com.wwz.frame.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description clientDetail 字段 与默认方法一致  对应数据库oauth_client_details   采用自带的
 * @Author wwz
 * @Date 2019/07/26
 * @Param
 * @Return
 */
@Data
@ApiModel(value="AuthClientDetails",description = "客户端实体")
public class AuthClientDetails implements Serializable {
    @ApiModelProperty(value = "自增id", name = "id",required = true)
    private Integer id;
    @ApiModelProperty(value = "客户端id", name = "clientId", required = true)
    private String clientId; //
    @ApiModelProperty(value = "客户端密码", name = "clientSecret", required = true)
    private String clientSecret; //
    @ApiModelProperty(value = "资源范围 传值格式示例 auth,audit", name = "resourceIds")
    private String resourceIds; //
    @ApiModelProperty(value = "资源范围 传值格式示例 auth，audit", name = "scopes")
    private String scopes; //
    @ApiModelProperty(value = "授权类型 （四种多选或单选） 传值示例 password,authorization_code,client_credentials,refresh_token", name = "authorizedGrantTypes", required = true)
    private String authorizedGrantTypes; //
    @ApiModelProperty(value = "code返回地址  示例（url为返回地址） url1,url2", name = "webServerRedirectUris")
    private String webServerRedirectUris; //
    @ApiModelProperty(value = "权限范围 示例 auth，audit", name = "authorities")
    private String authorities; //
    @ApiModelProperty(value = "token有效时间 秒", name = "accessTokenValidity",required = true)
    private Integer accessTokenValidity; //
    @ApiModelProperty(value = "刷新token有效时间 秒", name = "refreshTokenValidity",required = true)
    private Integer refreshTokenValidity; //
    @ApiModelProperty(hidden = true)
    private String additionalInformation; //
    @ApiModelProperty(hidden = true)
    private String autoApprove;  // 是否自动授权  默认false
    @ApiModelProperty(hidden = true)
    private Boolean valid;
}
