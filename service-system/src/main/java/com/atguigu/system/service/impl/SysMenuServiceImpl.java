package com.atguigu.system.service.impl;

import com.atguigu.common.result.Result;
import com.atguigu.common.utils.RouterHelper;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.model.vo.RouterVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysRoleMenuMapper;
import com.atguigu.system.mapper.SysUserRoleMapper;
import com.atguigu.system.service.SysMenuService;
import com.atguigu.system.utils.MenuHelper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2024-03-20
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {


    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {

        //全部权限列表
        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        //构建树形数据
       List<SysMenu> result = MenuHelper.buildTree(sysMenuList);

        return result;
    }

    @Override
    public void removeMenuById(String id) {
        //先查询是否有子菜单
        QueryWrapper<SysMenu> wrapper = new QueryWrapper();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        
        //如果有子菜单，则抛出异常信息
        if(count > 0){
            throw new GuiguException(201,"请先删除子菜单");
        }
        //没有子菜单则进行删除
        baseMapper.deleteById(id);
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(String roleId) {
        //获取所有status为1的权限列表
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        List<SysMenu> menuList = baseMapper.selectList(wrapper);

        //根据角色id获取角色权限
        QueryWrapper<SysRoleMenu> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("role_id",roleId);
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(wrapper1);
        //获取该角色已分配的所有权限id
        List<String> roleMenuIds = new ArrayList<>();
        for ( SysRoleMenu roleMenu: roleMenus) {
            roleMenuIds.add(roleMenu.getMenuId());
        }
        //遍历所有权限列表
        for (SysMenu menu : menuList) {
            if(roleMenuIds.contains(menu.getId())){
                menu.setSelect(true);
            }else {
                menu.setSelect(false);
            }
        }
        //将权限列表转换为权限树

        List<SysMenu> sysMenus = MenuHelper.buildTree(menuList);

        return sysMenus;
    }

    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //删除已分配的权限
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",assginMenuVo.getRoleId());
        sysRoleMenuMapper.delete(wrapper);
        //遍历所以已分配权限的id
        for (String menuId : assginMenuVo.getMenuIdList()) {
            //如果menuId不为null
            if(menuId != null){
                //创建SysRoleMenu对象
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
                sysRoleMenu.setMenuId(menuId);
                //添加到数据库
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }

    @Override
    public List<RouterVo> getUserMenuList(String id) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if(Long.valueOf(id) == 1){
            //如果是超级管理员查询所有
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>()
                            .eq("status", 1)
                            .orderByAsc("sort_value"));
        }else {
            //如果不是超级管理员，则查询对应的权限
            sysMenuList = baseMapper.findListByUserId(id);
        }

        //构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        //构建路由
        List<RouterVo> routerVoList = RouterHelper.buildRouters(sysMenuTreeList);

        return routerVoList;
    }

    @Override
    public List<String> findUserPermsList(String id) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if("1".equals(id)){
            //如果是超级管理员查询所有
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>()
                    .eq("status", 1));
        }else {
            sysMenuList = baseMapper.findListByUserId(id);
        }
        //创建返回的集合
        List<String> permissionList = new ArrayList<>();
        for (SysMenu sysMenu: sysMenuList) {

            //type == 2
            if(sysMenu.getType() == 2){
                String perms = sysMenu.getPerms();
                permissionList.add(perms);
            }

        }
        return permissionList;
    }
}
