package com.wwz.frame.service;

import com.wwz.frame.entity.AuthUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**  
* @Description 为默认的token登录方式提供数据
* @Author wwz
* @Date 2019/08/05 
* @Param   
* @Return   
*/ 
@Service
public class MyUsernameUserDetailsService extends MyUserDetailsService {
    @Override
    protected AuthUser getUser(String var1) {
        // 账号密码登录根据用户名查询用户
        AuthUser authUser = authUserMapper.selectByUsername(var1);
        if (authUser == null) {
            throw new UsernameNotFoundException("找不到该用户,用户名：" + var1);
        }
        return authUser;
    }
}
