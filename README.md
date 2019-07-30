2019.07.02

最近在使用SpringCloud 开发权限管理项目，于是设计了一个SpringSecurity OAuth2的统一安全认证。
1、环境   SpringBoot 2.1.0.RELEASE jdk1.8   SpringCloud Greenwich.SR1  consul服务发现与注册  
2、项目划分

        （1）、fp-commons  jar公用
        （2）、fp-gateway 网关
        （3）、fp-resource-manager 用户信息管理资源服务器
        （4）、fp-authorization-server OAuth2认证服务器
        （5）、fp-log 日志
3、 项目搭建     
   （1）网关搭建 frame-gateway    
   
        pom.xml：
            引入依赖文件；在启动器上添加 注册监听@EnableDiscoveryClient 
            并注入 DiscoveryClientRouteDefinitionLocator 
        application.xml： 
            spring-main-allow-bean-definition-overriding: true （相同名称注入允许覆盖）
            spring.application.name:SpringCloud-consul-gateway   （设置应用名称必须）
            bootstrap.yml 中配置 consul（比application先加载） 
            prefer-ip-address: true （使用ip注册，有些网络会出现用主机名来获取注册的问题）
   (2)fp-commons  jar公用 
   
        pom.xml：
              引入依赖文件；添加一些公用方法
   (3)fp-authorization-server
   
   （git中提交了一个至简版本的OAuth2认证中心，只有token生成，没有资源保护以及4种获取token方式（password、authorization_code、refresh_token、client_credentials））
        
        只需要配置AuthorizationServerConfigurerAdapter 认证服务器
        以及WebSecurityConfigurerAdapter SpringSecurity配置 两个文件，就能实现token生成。
        如果没有需要保护的资源不用ResourceServerConfigurerAdapter 资源服务器配置
        
   AuthorizationServerConfigurerAdapter  认证适配器有三个主要的方法：
   
   1、AuthorizationServerSecurityConfigurer：配置令牌端点（Token Endpoint）的安全约束 
   
   （这里我配置了一个处理请求携带客户端信息是否完整的过滤器）
   
   2、ClientDetailsServiceConfigurer:配置客户端详细服务， 客户端的详情在这里进行初始化
   
   （这里我新建了MyClientDetailsService自定义重写了ClientDetailsService接口的loadClientByClientId方法）
   
        客户端数据是通过ClientDetailsService接口来管理的，默认有两种方式分别是
        InMemoryClientDetailsServiceBuilder  写入内存
        JdbcClientDetailsServiceBuilder      写入和数据库
         
   3、AuthorizationServerEndpointsConfigurer：配置授权（authorization）以及令牌（token）的访问端点和令牌服务（token services）
   
   （这里我新建了MyUserDetailsService 自定义重写了UserDetailsService的 loadUserByUsername方法）
   
        用户数据是通UserDetailsService接口来管理的，默认有两种凡是分别是 
        InMemoryUserDetailsManager
        JdbcUserDetailsManager（有默认的表结构需要在数据库中新建）两种对用户数据的管理实现方式
        
   
   基于自己的项目，我们可能需要定制化一些自己的功能：
   
   1、请求前客户端信息完整性校验  
      对于携带数据不完整的请求，可以直接返回给前端，不需要经过后面的验证
      新建一个ClientDetailsAuthenticationFilter 过滤器，在安全约束中加载到过滤链中
   
   2、自定义异常返回
   
        默认异常翻译为这种形式：
        {
            "error": "invalid_grant",
            "error_description": "Bad credentials"
        }
        我们期望的格式： 
        {
            "code":401,
            "msg":"msg"
        }
   
   
   新建MyOAuth2WebResponseExceptionTranslator实现 WebResponseExceptionTranslator接口
   
   重写ResponseEntity<Oauth2Exception> translate(Exception e)方法； 认证发送的异常在这里捕获，认证发生的异常在这里能捕获到，在这里我们可以将我们的异常信息封装成统一的格式返回即可，这里怎么处理因项目而异，这里我直接复制了DefaultWebResponseExceptionTranslator 实现方法
   
   定义自己的OAuth2Exception  MyOAuth2Exception
   
   定义异常的MyOAuth2Exception的序列化类 MyOAuth2ExceptionJacksonSerializer

   将定义好的异常处理  加入到授权服务器的 AuthorizationServerEndpointsConfigurer配置中
   
            效果：
            {
                "code": 400,
                "msg": "error=\"invalid_request\", error_description=\"Missing grant type\""
            }    
   3、无权处理器    
       
      默认的无权访问返回格式是：  
      {
          "error": "access_denied",
          "error_description": "不允许访问"
      }
      我们期望的格式是：
      {
         "code":401,
         "msg":"msg"
      }
   新建一个MyAccessDeniedHandler实现AccessDeniedHandler设置返回信息
   
   加入资源配置 ResourceServerConfigurerAdapter
   
   http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
   
   4、token失效处理器
      
      默认的token无效返回信息是：
      {
          "error": "invalid_token",
          "error_description": "Invalid access token: 78df4214-8e10-46ae-a85b-a8f5247370a"
      }
      我们期望的格式是： 
      {
         "code":403,
         "msg":"msg"
      }
   新建MyTokenExceptionEntryPoint 实现AuthenticationEntryPoint    
   在 ResourceServerConfigurerAdapter 中注入
   
        @Override
         public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
             resources.authenticationEntryPoint(tokenExceptionEntryPoint); // token失效处理器
             resources.resourceId("auth"); // 设置资源id  通过client的 scope 来判断是否具有资源权限
         }
           
        
        
        
        
        
       