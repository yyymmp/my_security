package com.jlz.security.demo.service;

import com.jlz.security.demo.dao.UserDao;
import com.jlz.security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author jlz
 * @className: UserService
 * @date 2021/11/18 16:35
 * @description todo
 **/
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //密码验证不在这里 在框架层次自动验证
        return user;
    }
}
