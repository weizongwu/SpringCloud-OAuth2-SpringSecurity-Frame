package com.wwz.frame.mapper;

import com.wwz.frame.entity.AuthPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface AuthPermissionMapper {
    @Select("select *from auth_permission ap inner join auth_role_permission arp on ap.id= arp.auth_permission_id where arp.auth_role_id =#{authRoleId} ")
    List<AuthPermission> listAuthPermissionsByAuthRoleId(@Param("authRoleId") Integer authRoleId);
}
