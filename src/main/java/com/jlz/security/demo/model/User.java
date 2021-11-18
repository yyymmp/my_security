package com.jlz.security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author jlz
 * @className: User
 * @date 2021/11/18 16:18
 * @description todo
 **/
@Data
@Entity(name = "t_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    /**
     * 多对多关系
     */
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<Role> roles;

    /**
     * 返回用户角色
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.getName()));
        }

        return list;
    }

    /**
     * 返回用户密码
     * @return
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 返回用户名
     * @return
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 是否未过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * 是否未锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * 密码是否未过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * 是否可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
