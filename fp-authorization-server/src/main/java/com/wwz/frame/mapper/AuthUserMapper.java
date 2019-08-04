package com.wwz.frame.mapper;

import com.wwz.frame.entity.AuthUser;
import org.apache.ibatis.annotations.*;


public interface AuthUserMapper {
    @Select("select *from auth_user where id=#{id}")
    @Results({
            @Result(property = "authRoles", column = "id",
                    many = @Many(select = "com.wwz.frame.mapper.AuthRoleMapper.listAuthRoleByAuthUserId"))
    })
    AuthUser selectById(@Param("id") Integer id);

    @Select("select *from auth_user where username=#{username}")
    AuthUser selectByUsername(@Param("username") String username);

    @Select("select *from auth_user where phone=#{phone}")
    AuthUser selectByPhone(@Param("phone") String phone);
}
