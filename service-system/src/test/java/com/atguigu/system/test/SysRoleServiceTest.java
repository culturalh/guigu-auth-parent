package com.atguigu.system.test;

import com.atguigu.model.system.SysRole;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class SysRoleServiceTest {

    @Autowired
    private SysRoleService sysRoleService;

    //查询所有
    @Test
    public void findAll(){
        List<SysRole> list = sysRoleService.list();
        System.out.println(list);
    }

    //增加操作
    @Test
    public void  add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode("testManager5");
        sysRole.setRoleName("测试角色5");
        sysRole.setDescription("测试角色5");
        boolean b = sysRoleService.save(sysRole);
        System.out.println(b);
    }

    //修改操作
    @Test
    public void update(){
        SysRole sysRole = sysRoleService.getById("1766729427212845058");

        sysRole.setDescription("测试员");

        boolean b = sysRoleService.updateById(sysRole);

        System.out.println(b);


    }

    //删除操作
    @Test
    public void remove(){

        boolean b = sysRoleService.removeById("1766729427212845058");

        System.out.println(b);
    }
    //条件查询
    @Test
    public void select(){

        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name","系统管理员");
        List<SysRole> list = sysRoleService.list(queryWrapper);
        System.out.println(list);

    }
}
