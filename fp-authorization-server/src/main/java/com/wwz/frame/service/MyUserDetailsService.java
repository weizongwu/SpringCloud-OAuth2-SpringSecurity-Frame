package com.wwz.frame.service;

import com.wwz.frame.entity.AuthPermission;
import com.wwz.frame.entity.AuthRole;
import com.wwz.frame.entity.AuthUser;
import com.wwz.frame.mapper.AuthUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description 自定义用户验证数据
 * @Author wwz
 * @Date 2019/07/28
 * @Param
 * @Return
 */

@Service
public abstract class MyUserDetailsService implements UserDetailsService {

    @Autowired
    protected AuthUserMapper authUserMapper;

    @Override
    public UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException {
        // 自定义用户权限数据
        AuthUser authUser1 = getUser(var1);

        AuthUser authUser = authUserMapper.selectById(authUser1.getId());
        if (authUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        if (!authUser.getValid()) {
            throw new UsernameNotFoundException("用户不可用");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (authUser.getAuthRoles() != null) {
            for (AuthRole role : authUser.getAuthRoles()) {
                // 当前角色可用
                if (role.getValid()) {
                    //角色必须是ROLE_开头
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
                    grantedAuthorities.add(grantedAuthority);
                    if (role.getAuthPermissions() != null) {
                        for (AuthPermission permission : role.getAuthPermissions()) {
                            // 当前权限可用
                            if (permission.getValid()) {
                                // 拥有权限设置为   auth/member/GET  可以访问auth服务下面 member的查询方法
                                GrantedAuthority authority = new SimpleGrantedAuthority(permission.getServicePrefix() + "/" + permission.getUri() + "/" + permission.getMethod());
                                grantedAuthorities.add(authority);
                            }
                        }
                    }
                }
                //获取权限
            }
        }
        MyUserDetails userDetails = new MyUserDetails(authUser, grantedAuthorities);
        return userDetails;
    }


    protected abstract AuthUser getUser(String var1);
}
