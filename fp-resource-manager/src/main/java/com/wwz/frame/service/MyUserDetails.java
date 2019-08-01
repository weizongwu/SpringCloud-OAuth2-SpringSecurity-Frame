package com.wwz.frame.service;

import com.wwz.frame.entity.AuthUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Description 自定义实现UserDetails 资源服务器中用于redis反序列化用户信息
 * @Author wwz
 * @Date 2019/08/01
 * @Param
 * @Return
 */
@Data
public class MyUserDetails implements UserDetails {
    private AuthUser user;

    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(AuthUser user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public MyUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getValid();
    }
}
