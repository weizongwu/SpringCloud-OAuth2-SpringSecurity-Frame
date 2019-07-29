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
        
        
        
       