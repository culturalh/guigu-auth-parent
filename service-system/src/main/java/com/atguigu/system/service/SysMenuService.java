package com.atguigu.system.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.model.vo.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2024-03-20
 */
public interface SysMenuService extends IService<SysMenu> {

    //角色列表
    List<SysMenu> findNodes();

    //删除菜单
    void removeMenuById(String id);

    //根据角色id获取菜单
    List<SysMenu> findSysMenuByRoleId(String roleId);

    //给角色分配权限
    void doAssign(AssginMenuVo assginMenuVo);

    //根据用户id查询菜单信息
    List<RouterVo> getUserMenuList(String id);

    //根据用户id查询按钮信息
    List<String> findUserPermsList(String id);

}
