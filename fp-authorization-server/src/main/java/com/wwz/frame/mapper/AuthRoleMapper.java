package com.wwz.frame.mapper;

import com.wwz.frame.entity.AuthRole;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface AuthRoleMapper {
    @Select("select *from auth_role ar inner join auth_user_role aur on ar.id=auth_role_id  where aur.auth_user_id=#{authUserId}")
    @Results({
            @Result(property = "authPermissions", column = "id",
                    many = @Many(select = "com.wwz.frame.mapper.AuthPermissionMapper.listAuthPermissionsByAuthRoleId"))
    })
    List<AuthRole> listAuthRoleByAuthUserId(@Param("authUserId") Integer authUserId);
}
