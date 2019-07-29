package com.wwz.frame.service;

import com.wwz.frame.common.CommonUtils;
import com.wwz.frame.entity.AuthClientDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * @Description 自定义实现ClientDetails  不能使用lombok
 * @Author wwz
 * @Date 2019/07/28
 * @Param
 * @Return
 */
public class MyClientDetails implements ClientDetails {
    private AuthClientDetails client;

    public MyClientDetails(AuthClientDetails client) {
        this.client = client;
    }

    public MyClientDetails() {

    }

    /**
     * The client id.
     *
     * @return The client id.
     */
    @Override
    public String getClientId() {
        return client.getClientId();
    }

    /**
     * The resources that this client can access. Can be ignored by callers if empty.
     *
     * @return The resources of this client.
     */
    @Override
    public Set<String> getResourceIds() {
        return client.getResourceIds() != null ?
                CommonUtils.transformStringToSet(client.getResourceIds(), String.class) :  Collections.emptySet();
    }

    /**
     * Whether a secret is required to authenticate this client.
     *
     * @return Whether a secret is required to authenticate this client.
     */
    @Override
    public boolean isSecretRequired() {
        return client.getClientSecret() != null;
    }

    /**
     * The client secret. Ignored if the {@link #isSecretRequired() secret isn't required}.
     *
     * @return The client secret.
     */
    @Override
    public String getClientSecret() {
        return client.getClientSecret();
    }

    /**
     * Whether this client is limited to a specific scope. If false, the scope of the authentication request will be
     * ignored.
     *
     * @return Whether this client is limited to a specific scope.
     */
    @Override
    public boolean isScoped() {
        return this.getScope() != null && !this.getScope().isEmpty();
    }

    /**
     * The scope of this client. Empty if the client isn't scoped.
     *
     * @return The scope of this client.
     */
    @Override
    public Set<String> getScope() {
        return client.getScopes() != null ?
                CommonUtils.transformStringToSet(client.getScopes(), String.class) :  Collections.emptySet();
    }

    /**
     * The grant types for which this client is authorized.
     *
     * @return The grant types for which this client is authorized.
     */
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return client.getAuthorizedGrantTypes() != null ?
                CommonUtils.transformStringToSet(client.getAuthorizedGrantTypes(), String.class) :  Collections.emptySet();
    }

    /**
     * The pre-defined redirect URI for this client to use during the "authorization_code" access grant. See OAuth spec,
     * section 4.1.1.
     *
     * @return The pre-defined redirect URI for this client.
     */
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return client.getWebServerRedirectUris() != null ?
                CommonUtils.transformStringToSet(client.getWebServerRedirectUris(), String.class) : Collections.emptySet();
    }

    /**
     * Returns the authorities that are granted to the OAuth client. Cannot return <code>null</code>.
     * Note that these are NOT the authorities that are granted to the user with an authorized access token.
     * Instead, these authorities are inherent to the client itself.
     *
     * @return the authorities (never <code>null</code>)
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return (client.getAuthorities() != null && client.getAuthorities().trim().length() > 0) ?
                AuthorityUtils.commaSeparatedStringToAuthorityList(client.getAuthorities()) : Collections.emptyList();
    }

    /**
     * The access token validity period for this client. Null if not set explicitly (implementations might use that fact
     * to provide a default value for instance).
     *
     * @return the access token validity period
     */
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return client.getAccessTokenValidity();
    }

    /**
     * The refresh token validity period for this client. Null for default value set by token service, and
     * zero or negative for non-expiring tokens.
     *
     * @return the refresh token validity period
     */
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return client.getRefreshTokenValidity();
    }

    /**
     * Test whether client needs user approval for a particular scope.
     *
     * @param scope the scope to consider
     * @return true if this client does not need user approval
     */
    @Override
    public boolean isAutoApprove(String scope) {
        if (this.getAutoApproveScopes() == null) {
            return false;
        } else {
            Iterator var2 = this.getAutoApproveScopes().iterator();
            String auto;
            do {
                if (!var2.hasNext()) {
                    return false;
                }
                auto = (String) var2.next();
            } while (!auto.equals("true") && !scope.matches(auto));
            return true;
        }
    }

    /**
     * Additional information for this client, not needed by the vanilla OAuth protocol but might be useful, for example,
     * for storing descriptive information.
     *
     * @return a map of additional information
     */
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }

    public Set<String> getAutoApproveScopes() {
        return client.getAutoApprove() != null ?
                CommonUtils.transformStringToSet(client.getAutoApprove(), String.class) :  Collections.emptySet();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((client.getAccessTokenValidity() == null) ? 0
                : client.getAccessTokenValidity());
        result = prime
                * result
                + ((client.getRefreshTokenValidity() == null) ? 0
                : client.getRefreshTokenValidity());
        result = prime * result
                + ((client.getAuthorities() == null) ? 0 : client.getAuthorities().hashCode());
        result = prime
                * result
                + ((client.getAuthorizedGrantTypes() == null) ? 0 : client.getAuthorizedGrantTypes()
                .hashCode());
        result = prime * result
                + ((client.getClientId() == null) ? 0 : client.getClientId().hashCode());
        result = prime * result
                + ((client.getClientSecret() == null) ? 0 : client.getClientSecret().hashCode());
        result = prime
                * result
                + ((client.getWebServerRedirectUris() == null) ? 0
                : client.getWebServerRedirectUris().hashCode());
        result = prime * result
                + ((client.getResourceIds() == null) ? 0 : client.getResourceIds().hashCode());
        result = prime * result + ((client.getScopes() == null) ? 0 : client.getScopes().hashCode());
        result = prime * result + ((client.getAdditionalInformation() == null) ? 0 : client.getAdditionalInformation().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AuthClientDetails other = (AuthClientDetails) obj;
        if (client.getAccessTokenValidity() == null) {
            if (other.getAccessTokenValidity() != null)
                return false;
        } else if (!client.getAccessTokenValidity().equals(other.getAccessTokenValidity()))
            return false;
        if (client.getRefreshTokenValidity() == null) {
            if (other.getRefreshTokenValidity() != null)
                return false;
        } else if (!client.getRefreshTokenValidity().equals(other.getRefreshTokenValidity()))
            return false;
        if (client.getAuthorities() == null) {
            if (other.getAuthorities() != null)
                return false;
        } else if (!client.getAuthorities().equals(other.getAuthorities()))
            return false;
        if (client.getAuthorizedGrantTypes() == null) {
            if (other.getAuthorizedGrantTypes() != null)
                return false;
        } else if (!client.getAuthorizedGrantTypes().equals(other.getAuthorizedGrantTypes()))
            return false;
        if (client.getClientId() == null) {
            if (other.getClientId() != null)
                return false;
        } else if (!client.getClientId().equals(other.getClientId()))
            return false;
        if (client.getClientSecret() == null) {
            if (other.getClientSecret() != null)
                return false;
        } else if (!client.getClientSecret().equals(other.getClientSecret()))
            return false;
        if (client.getWebServerRedirectUris() == null) {
            if (other.getWebServerRedirectUris() != null)
                return false;
        } else if (!client.getWebServerRedirectUris().equals(other.getWebServerRedirectUris()))
            return false;
        if (client.getResourceIds() == null) {
            if (other.getResourceIds() != null)
                return false;
        } else if (!client.getResourceIds().equals(other.getResourceIds()))
            return false;
        if (client.getScopes() == null) {
            if (other.getScopes() != null)
                return false;
        } else if (!client.getScopes().equals(other.getScopes()))
            return false;
        if (client.getAdditionalInformation() == null) {
            if (other.getAdditionalInformation() != null)
                return false;
        } else if (!client.getAdditionalInformation().equals(other.getAdditionalInformation()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AuthClientDetails [clientId=" + client.getClientId() + ", clientSecret="
                + client.getClientSecret() + ", scope=" + client.getScopes() + ", resourceIds="
                + client.getResourceIds() + ", authorizedGrantTypes="
                + client.getAuthorizedGrantTypes() + ", registeredRedirectUris="
                + client.getWebServerRedirectUris() + ", authorities=" + client.getAuthorities()
                + ", accessTokenValiditySeconds=" + client.getAccessTokenValidity()
                + ", refreshTokenValiditySeconds="
                + client.getRefreshTokenValidity() + ", additionalInformation="
                + client.getAdditionalInformation() + "]";
    }


}
