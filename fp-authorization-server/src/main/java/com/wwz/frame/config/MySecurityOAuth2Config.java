package com.wwz.frame.config;

import com.wwz.frame.exception.MyOAuth2WebResponseExceptionTranslator;
import com.wwz.frame.filter.ClientDetailsAuthenticationFilter;
import com.wwz.frame.service.MyClientDetailsService;
import com.wwz.frame.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Description OAuth2认证服务配置
 * @Author wwz
 * @Date 2019/07/28
 * @Param
 * @Return
 */
@Configuration
@EnableAuthorizationServer
public class MySecurityOAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;    // 认证方法入口

    @Autowired
    private RedisConnectionFactory connectionFactory;  // redis连接工厂

    @Autowired
    private MyUserDetailsService userDetailsService;  // 自定义用户验证数据

    @Autowired
    private MyClientDetailsService clientDetailsService; // 自定义客户端数据
    @Autowired
    private ClientDetailsAuthenticationFilter clientDetailsAuthenticationFilter; // 客户端认证之前的过滤器

    @Autowired
    private MyOAuth2WebResponseExceptionTranslator webResponseExceptionTranslator; // 自定义返回异常格式

    // 加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /***
     * 设置token用redis保存
     */
    @Bean
    public TokenStore tokenStore() {
        //token保存在redis中（也可以保存在数据库、内存中new InMemoryTokenStore()、或者jwt;）。
        //如果保存在中间件（数据库、Redis），那么资源服务器与认证服务器可以不在同一个工程中。
        //注意：如果不保存access_token，则没法通过access_token取得用户信息
        RedisTokenStore redis = new RedisTokenStore(connectionFactory);
        return redis;
    }

    /**
     * 配置令牌端点（Token Endpoint）的安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        // 加载client的 获取接口
        clientDetailsAuthenticationFilter.setClientDetailsService(clientDetailsService);
        // 客户端认证之前的过滤器
        oauthServer.addTokenEndpointAuthenticationFilter(clientDetailsAuthenticationFilter);
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();   // 允许表单登录
    }

    /**
     * 配置 oauth_client_details【client_id和client_secret等】信息的认证【检查ClientDetails的合法性】服务
     * 设置 认证信息的来源：数据库，内存，也可以自己实现ClientDetailsService的loadClientByClientId 方法自定义数据源
     * 自动注入：ClientDetailsService的实现类 JdbcClientDetailsService (检查 ClientDetails 对象)
     * 这个方法主要是用于校验注册的第三方客户端的信息，可以存储在数据库中，默认方式是存储在内存中
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 设置从默认自带数据连接方式取
        clients.withClientDetails(clientDetailsService);
// clients.inMemory() // 使用in-memory存储
//                .withClient("client_name") // client_id
//                .secret(passwordEncoder().encode("111")) // client_secret
//                .redirectUris("http://localhost:8001")
//                // 该client允许的授权类型
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials")
//                .scopes("app", "app1", "app3"); // 允许的授权范围
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务（token services）
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//      endpoints.pathMapping("/oauth/token","/token/login");  设置token生成请求地址
        endpoints
                .tokenStore(tokenStore())  // 配置token存储
                .userDetailsService(userDetailsService)  // 配置自定义的用户权限数据，不配置会导致token无法刷新
                .authenticationManager(authenticationManager)
                .tokenServices(defaultTokenServices())// 加载token配置
                .exceptionTranslator(webResponseExceptionTranslator);  // 自定义异常返回
    }

    /**
     * 把认证的token保存到redis
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * 自定义的token
     * 认证的token是存到redis里的 若ClientDetails中设定了有效时间则以设定值为准
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenEnhancer(tokenEnhancer());    // token自定义
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 12);  // token有效期自定义设置，默认12小时
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);  // refresh_token默认30天
        return tokenServices;
    }
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new MyTokenEnhancer();
    }
}
