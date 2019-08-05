package com.wwz.frame.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * @Description WebResponseExceptionTranslator
 * @Author wwz
 * @Date 2019/07/30
 * @Param
 * @Return
 */
@Component("MyOAuth2WebResponseExceptionTranslator")
public class MyOAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity.status(oAuth2Exception.getHttpErrorCode())
                .body(new MyOAuth2Exception(oAuth2Exception.getMessage()));
    }
}
