package com.wwz.frame.filter;

import com.wwz.frame.token.MyPhoneAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Description 手机验证码：post /token/login?type=phoneNumber&phoneNumber=15000000000&phoneCode=1234
* @Author wwz
* @Date 2019/08/04
* @Param
* @Return
*/
public class MyPhoneLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String PHONE_NUMBER_KEY = "phoneNumber"; // 手机号码

    private static final String PHONE_NUMBER_CODE_KEY = "phoneCode"; // 验证码

    private boolean postOnly = true;

    private static final String LOGIN_URL = "/token/phoneLogin";

    public MyPhoneLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher(LOGIN_URL, "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        AbstractAuthenticationToken authRequest;
        // 手机验证码登陆
        String principal =RequestUtil(request,PHONE_NUMBER_KEY);
        String credentials =RequestUtil(request,PHONE_NUMBER_CODE_KEY);



        principal = principal.trim();
        authRequest = new MyPhoneAuthenticationToken(principal, credentials);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request,
                            AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String RequestUtil(HttpServletRequest request, String parameter) {
        String result = request.getParameter(parameter);
        return result == null ? "" : result;
    }
}
