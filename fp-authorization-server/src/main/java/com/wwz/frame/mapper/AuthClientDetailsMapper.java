package com.wwz.frame.mapper;

import com.wwz.frame.entity.AuthClientDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface AuthClientDetailsMapper {
    @Select("select *from auth_client_details where client_id = #{clientId}")
    AuthClientDetails selectClientDetailsByClientId(@Param("clientId") String clientId);
}
