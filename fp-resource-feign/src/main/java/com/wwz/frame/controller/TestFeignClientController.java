package com.wwz.frame.controller;

import com.wwz.frame.feignclient.ResourceManagerFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description FeignClient 测试类
 * @Author wwz
 * @Date 2019/11/20
 * @Email wwzwtf@qq.com
 * @Param
 * @Return
 */
@RestController
@RequestMapping("feign")
@Slf4j
public class TestFeignClientController {
    @Autowired
    private ResourceManagerFeignClient resourceManagerFeignClient;

    @GetMapping("hello")
    public String hello() {
        log.info("进入方法");
        return resourceManagerFeignClient.hello();
    }
}
