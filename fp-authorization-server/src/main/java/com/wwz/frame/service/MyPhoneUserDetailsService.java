package com.wwz.frame.service;

import com.wwz.frame.entity.AuthUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyPhoneUserDetailsService extends MyUserDetailsService {
    @Override
    protected AuthUser getUser(String var1) {
        // 手机验证码登录使用，根据手机号码查询用户信息
        AuthUser authUser = authUserMapper.selectByPhone(var1);
        if (authUser == null) {
            throw new UsernameNotFoundException("找不到该用户，手机号码有误：" + var1);
        }
        return authUser;
    }
}
