package com.atguigu.system.service.impl;


import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.RouterVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.mapper.SysUserMapper;
import com.atguigu.system.service.SysMenuService;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2024-03-17
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysMenuService sysMenuService;

    //条件分页查询
    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo) {
        IPage<SysUser> pageModel = baseMapper.selectPage(pageParam, sysUserQueryVo);
        return pageModel;
    }

    //更新用户状态
    @Override
    public void updateStatus(Long id, Integer status) {
        //根据id查询
        SysUser sysUser = baseMapper.selectById(id);
        //修改状态
        sysUser.setStatus(status);
        //通过id更新用户信息
        baseMapper.updateById(sysUser);
    }

    @Override
    public SysUser getByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        SysUser sysUser = baseMapper.selectOne(wrapper);
        System.out.println(sysUser);
        return sysUser;
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        //根据username查询用户信息
        System.out.println(this);
        SysUser sysUser = this.getByUsername(username);

//        System.out.println(sysUser.toString()+"---------------------");
        //根据用户id查询菜单信息
        List<RouterVo> routerVoList = sysMenuService.getUserMenuList(sysUser.getId());
//        System.out.println(routerVoList+"--------------------");
        //根据用户id查询按钮信息
        List<String> permsList = sysMenuService.findUserPermsList(sysUser.getId());
//        System.out.println(permsList+"--------------------");

        Map<String,Object> result = new HashMap<>();
        result.put("username",username);
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles","[admin]");

        //菜单权限
        result.put("routers",routerVoList);
        //按钮权限
        result.put("buttons",permsList);
        //        Map<String, Object> map = new HashMap<>();
//        map.put( "roles","[admin]");
//        map.put("introduction", "I am a super administrator");
//        map.put( "avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
//        map.put("name", "Super Admin atguigu");

        return result;
    }
}
