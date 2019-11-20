package com.wwz.frame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description
 * @Author wwz
 * @Date 2019/11/20
 * @Email wwzwtf@qq.com
 * @Param
 * @Return
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FeignClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignClientApplication.class,args);
    }
}
