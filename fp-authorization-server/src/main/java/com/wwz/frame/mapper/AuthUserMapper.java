package com.wwz.frame.mapper;

import com.wwz.frame.entity.AuthUser;
import org.apache.ibatis.annotations.*;


public interface AuthUserMapper {
    @Select("select *from auth_user where username=#{username}")
    @Results({
            @Result(property = "authRoles", column = "id",
                    many = @Many(select = "com.wwz.frame.mapper.AuthRoleMapper.listAuthRoleByAuthUserId"))
    })
    AuthUser selectByUsername(@Param("username") String username);
}
