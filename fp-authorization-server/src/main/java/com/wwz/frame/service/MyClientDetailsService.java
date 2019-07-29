package com.wwz.frame.service;

import com.wwz.frame.entity.AuthClientDetails;
import com.wwz.frame.mapper.AuthClientDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
/**
* @Description 自定义客户端数据
* @Author wwz
* @Date 2019/07/28
* @Param
* @Return
*/
@Service
public class MyClientDetailsService implements ClientDetailsService {
    @Autowired
    private AuthClientDetailsMapper authClientDetailsMapper;

    /**
     * Load a client by the client id. This method must not return null.
     *
     * @param clientId The client id.
     * @return The client details (never null).
     * @throws ClientRegistrationException If the client account is locked, expired, disabled, or invalid for any other reason.
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        AuthClientDetails clientDetails = authClientDetailsMapper.selectClientDetailsByClientId(clientId);
        if (clientDetails == null) {
            throw new ClientRegistrationException("该客户端不存在");
        }
        MyClientDetails details = new MyClientDetails(clientDetails);
        return details;
    }
}
