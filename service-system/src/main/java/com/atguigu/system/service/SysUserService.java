package com.atguigu.system.service;


import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2024-03-17
 */
public interface SysUserService extends IService<SysUser> {

    //分页查询
    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);

    //跟新用户状态
    void updateStatus(Long id, Integer status);

    //通过登录用户名获取用户信息
    SysUser getByUsername(String username);

    //根据用户名称获取用户信息（基本信息 菜单权限 和 按钮权限数据）
    Map<String, Object> getUserInfo(String username);
}
