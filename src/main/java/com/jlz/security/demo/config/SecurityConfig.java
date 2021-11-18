package com.jlz.security.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                .successForwardUrl("/ok")
                .failureForwardUrl("/fail")
                .permitAll()
                .and()
                .logout()
                //默认就是logout get请求
//                .logoutUrl("/logout")
                //注销登录成功之后的跳转地址
                .logoutSuccessUrl("/login.html")
                //放行相关接口
                .permitAll()
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行静态资源文件
        web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
    }
}
