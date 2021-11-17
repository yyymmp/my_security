package com.jlz.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jlz
 * @className: HelloController
 * @date 2021/11/17 11:37
 * @description todo
 **/
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
}
