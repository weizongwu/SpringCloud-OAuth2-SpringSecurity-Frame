package com.wwz.frame.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description FeignClient 调用 fp-resource-manager项目
 * @Author wwz
 * @Date 2019/11/20
 * @Email wwzwtf@qq.com
 * @Param
 * @Return
 */
@FeignClient(value = "manager")
public interface ResourceManagerFeignClient {
    @GetMapping(value = "/auth/hello")
    String hello();
}
