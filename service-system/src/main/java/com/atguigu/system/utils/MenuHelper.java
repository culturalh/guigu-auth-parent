package com.atguigu.system.utils;

import com.atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * 根据菜单数据构建菜单树的工具类
 * </p>
 *
 */
public class MenuHelper {

    //使用递归方法创建菜单
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {

        List<SysMenu> trees = new ArrayList<>();
        for (SysMenu sysMenu:sysMenuList) {
            if(sysMenu.getParentId().longValue() == 0){
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    //递归查找子节点
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
        //  初始化
        sysMenu.setChildren(new ArrayList<>());
        for (SysMenu it : treeNodes) {
            if( Long.valueOf(sysMenu.getId()) == it.getParentId().longValue()){
                //判断是否为null
                if(sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                //递归查找
                sysMenu.getChildren().add(findChildren(it,treeNodes));

            }
        }


        return sysMenu;
    }
}
