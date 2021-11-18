package com.jlz.security.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author jlz
 * @className: Role
 * @date 2021/11/18 16:11
 * @description todo
 **/
@Data
@Entity(name = "t_role") //启动时自动创建该表
public class Role  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nameZh;
}
