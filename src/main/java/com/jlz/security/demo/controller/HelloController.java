package com.jlz.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jlz
 * @className: HelloController
 * @date 2021/11/17 11:37
 * @description todo
 **/
@RestController
public class HelloController {

    @PostMapping("/hello")
    public String hello(){
        System.out.println("hello");
        return "hello";
    }

    @GetMapping("/hello")
    public String login(){
        System.out.println("get login");
        return "login";
    }

    @PostMapping("/fail")
    public String fail(){
        System.out.println("login fail");
        return "login";
    }

    @PostMapping("/ok")
    public String ok(){
        System.out.println("login ok");
        return "login";
    }
}
