package com.wwz.frame.common;
/**  
* @Description 枚举返回
* @Author wwz
* @Date 2019/07/26
* @Param   
* @Return   
*/ 
public enum CodeEnum {
    ADD_SUCCESS(100,"新增成功"),
    ADD_FAIL(101,"新增失败！"),
    MODIFY_SUCCESS(110,"修改成功！"),
    MODIFY_FAIL(111,"修改失败！"),
    REMOVE_SUCCESS(120,"删除成功！"),
    REMOVE_FAIL(121,"删除失败！"),
    LOGIN_SUCCESS(130,"登录成功");

    private Integer code;
    private String msg;

    CodeEnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
