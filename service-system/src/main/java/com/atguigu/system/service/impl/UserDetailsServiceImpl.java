package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysUser;
import com.atguigu.system.custom.CustomUser;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysMenuService;
import com.atguigu.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名获取用户信息
        SysUser sysUser = sysUserService.getByUsername(username);
        //对sysUser进行判断
        if(sysUser == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        if(sysUser.getStatus().intValue() == 0){
            throw new RuntimeException("用户被禁用");
        }

        //根据userId查询操作权限数据
        List<String> userPermsList = sysMenuService.findUserPermsList(sysUser.getId());
        //转换成security格式数据
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String perms : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perms.trim()));
        }

        return new CustomUser(sysUser, authorities);
    }
}
