package com.wwz.frame.mapper;

import com.wwz.frame.entity.AuthUser;
import org.apache.ibatis.annotations.*;


public interface AuthUserMapper {
    @Insert("INSERT INTO auth_user(username,password,ch_name,create_time,update_time,valid)values(#{username},#{password},#{chName},now(),now(),1)")
    int insert(AuthUser authUser);

    @Select("select *from auth_user where username=#{username}")
    @Results({
            @Result(property = "authRoles", column = "id",
                    many = @Many(select = "com.wwz.frame.mapper.AuthRoleMapper.listAuthRoleByAuthUserId"))
    })
    AuthUser selectByUsername(@Param("username") String username);

}
