package com.jlz.security.demo.dao;

import com.jlz.security.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jlz
 * @className: UserDao
 * @date 2021/11/18 16:33
 * @description todo
 **/
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * 查询
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
