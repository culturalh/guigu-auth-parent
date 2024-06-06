package com.atguigu.system.test;


import com.atguigu.model.system.SysRole;
import com.atguigu.system.mapper.SysRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SysRoleMapperTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    //添加操作
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode("testManager3");
        sysRole.setRoleName("测试角色3");
        sysRole.setDescription("测试角色3");
        int row = sysRoleMapper.insert(sysRole);
        System.out.println(row);
    }

    //修改操作
    @Test
    public void update(){
        //根据id查询
        SysRole sysRole = sysRoleMapper.selectById("1766729427212845059");
        //设置修改值
        sysRole.setDescription("高级工程师");
        //进行修改
        int row = sysRoleMapper.updateById(sysRole);
        System.out.println(row);

    }

    //批量删除
    @Test
    public void BatchDelete(){
        int rows = sysRoleMapper.deleteBatchIds(Arrays.asList(1, 2));
        System.out.println(rows);
    }


    //删除操作
    @Test
    public void delete(){
        int row = sysRoleMapper.deleteById("1766729427212845058");
        System.out.println(row);
    }


    //查询所有数据
    @Test
    public void testFindAll(){
        List<SysRole> list = sysRoleMapper.selectList(null);
        for (SysRole sys :
                list) {
            System.out.println(sys);
        }
    }

    //条件查询
    @Test
    public void testSelect(){
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("role_name","管理员");
        List<SysRole> list = sysRoleMapper.selectList(queryWrapper);
        System.out.println(list);
    }

    //条件删除
    @Test
    public void testDelete(){
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name","测试角色2");
        int rows = sysRoleMapper.delete(queryWrapper);
        System.out.println(rows);
    }

}
