package com.jlz.security.demo.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jlz
 * @className: SecurityConfig
 * @date 2021/11/17 15:38
 * @description todo
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        //不加密 已过期
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //基于内存的用户密码
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("123456")
                .roles("admin")
                .and()
                .withUser("jlz")
                .password("123456")
                .roles("admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义表单登录
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                //前后不分离情况
//                .successForwardUrl("/ok")
//                .failureForwardUrl("/fail")
                //前后分离情况 登录成功处理器
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    //authentication保存了登录成功之后的用户信息 getPrincipal
                    Object principal = authentication.getPrincipal();
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    //可以根据异常类型来确定具体的异常 框架层将所有的错误提示都设置为Bad credentials
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(e.getMessage()));
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .logout()
                //默认就是logout get请求
                .logoutUrl("/logout")
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    //处理注销成功
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    Map<String, String> map = new HashMap<String, String>(8);
                    map.put("msg", "注销成功");
                    out.write(JSON.toJSONString(map));
                    out.flush();
                    out.close();
                })
                //注销登录成功之后的跳转地址
//                .logoutSuccessUrl("/login.html")
                //放行相关接口
                .permitAll()
                .and()
                .csrf().disable()
                //处理未登录情况 因为现在使用前后端分离的方式 当访问未登录接口时,直接跳转到了login.html页面 不满足要求
                .exceptionHandling()
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    Map<String, String> map = new HashMap<String, String>(8);
                    map.put("msg", "未登录");
                    out.write(JSON.toJSONString(map));
                    out.flush();
                    out.close();
                });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行静态资源文件
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }
}
