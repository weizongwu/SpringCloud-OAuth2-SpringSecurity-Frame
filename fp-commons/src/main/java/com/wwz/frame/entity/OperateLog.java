package com.wwz.frame.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description 操作日志
 * @Author wwz
 * @Date 2019/07/26
 * @Param
 * @Return
 */
@Data
@ApiModel(value = "OperateLog", description = "操作日志实体")
public class OperateLog {
    @ApiModelProperty(hidden = true)
    private Integer id; // 自增字段
    @ApiModelProperty(value = "操作用户id", name = "operateId", required = true)
    private Integer operateId;
    @ApiModelProperty(value = "请求地址", name = "remoteAddr", required = true)
    private String remoteAddr;
    @ApiModelProperty(value = "请求方法", name = "requestUri", required = true)
    private String requestUri;
    @ApiModelProperty(value = "请求描述", name = "description", required = true)
    private String description;
    @ApiModelProperty(value = "请求方式", name = "method", required = true)
    private String method;
    @ApiModelProperty(value = "请求参数", name = "params")
    private String params;
    @ApiModelProperty(value = "返回值", name = "returnData", required = true)
    private String returnData;
    @ApiModelProperty(value = "异常信息", name = "exceptionMsg", required = true)
    private String exceptionMsg;
    @ApiModelProperty(hidden = true)
    private Date createTime; // 日志记录时间
}
