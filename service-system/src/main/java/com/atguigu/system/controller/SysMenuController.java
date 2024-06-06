package com.atguigu.system.controller;


import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2024-03-20
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    //根据角色id获取菜单
    @ApiOperation("根据角色id获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result  toAssign(@PathVariable String roleId){
      List<SysMenu> list = sysMenuService.findSysMenuByRoleId(roleId);
      return Result.ok(list);
    }


    //给角色分配权限
    @ApiOperation("给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo){
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }

    //菜单列表（树形）
    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public Result findNodes(){
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    //添加方法
    @ApiOperation("添加菜单")
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    //根据id查询列表
    @ApiOperation("根据id查询列表")
    @GetMapping("findNode/{id}")
    public Result findNode(@PathVariable String id){
        SysMenu sysMenu= sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }

    //修改菜单
    @ApiOperation("添加菜单")
    @PostMapping("/update")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    //根据id删除列表
    @ApiOperation("根据id删除列表")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id){
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }
}

