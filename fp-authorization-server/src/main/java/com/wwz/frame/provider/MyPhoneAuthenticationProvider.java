package com.wwz.frame.provider;


import com.wwz.frame.token.MyPhoneAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
* @Description 手机验证码登录
* @Author wwz
* @Date 2019/08/04
* @Param
* @Return
*/
public class MyPhoneAuthenticationProvider extends MyAbstractUserDetailsAuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails var1, Authentication authentication) throws AuthenticationException {

        if(authentication.getPrincipal() == null){
            throw new BadCredentialsException(this.messages.getMessage("MyPhoneAuthenticationProvider.badPrincipal", "Bad badPrincipal"));
        }
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException(this.messages.getMessage("MyPhoneAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String phoneCode = authentication.getCredentials().toString();
//            String phoneNumber = authentication.getPrincipal().toString();
//
//            String old_code = (String) redisTemplate.opsForValue().get(phoneNumber+"_code");
//
//            if(old_code==null){
//                // 验证码未获取或已经失效
//                throw new BadCredentialsException(this.messages.getMessage("MyPhoneAuthenticationProvider.badCredentials", "Bad phoneCode"));
//            }
//            if(!phoneCode.equals(old_code)){
//                // 验证码错误
//                throw new BadCredentialsException(this.messages.getMessage("MyPhoneAuthenticationProvider.badCredentials", "Bad phoneCode"));
//            }
            if (!"1234".equals(phoneCode)) {
                throw new BadCredentialsException(this.messages.getMessage("MyPhoneAuthenticationProvider.badCredentials", "Bad phoneCode"));
            }
        }
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        MyPhoneAuthenticationToken result = new MyPhoneAuthenticationToken(principal, authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    protected UserDetails retrieveUser(String phone, Authentication authentication) throws AuthenticationException {
        UserDetails loadedUser;
        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(phone);
        } catch (UsernameNotFoundException var6) {
            throw var6;
        } catch (Exception var7) {
            throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        } else {
            return loadedUser;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MyPhoneAuthenticationToken.class.isAssignableFrom(authentication);
    }


    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
