package com.wwz.frame.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {
    /**
     * 获取用户登陆信息
     */
    @GetMapping("/getAuthUser")
    public Principal user(Principal authUser) {
        return authUser;
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String hello(Principal principal) {
        return principal.getName() + " has hello Permission";
    }

    @GetMapping("/world")
    public String world() {
        return "world";
    }
}
