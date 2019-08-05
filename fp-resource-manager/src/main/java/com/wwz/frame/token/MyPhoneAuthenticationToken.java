package com.wwz.frame.token;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**  
* @Description 手机验证token
* @Author wwz
* @Date 2019/08/04 
* @Param   
* @Return   
*/
public class MyPhoneAuthenticationToken extends MyAuthenticationToken {

    public MyPhoneAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public MyPhoneAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
