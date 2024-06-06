package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.common.utils.JwtHelper;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    //{"code":20000,"data":{"token":"admin-token"}}
    @Autowired
    private SysUserService sysUserService;

    //登录
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        //通过登录用户名获取用户信息
        loginVo.getUsername();
        SysUser sysUser = sysUserService.getByUsername(loginVo.getUsername());
        System.out.println(sysUser.toString());
        if(sysUser == null){
            throw new GuiguException(201,"该用户不存在");
        }
        //把loginVo中的密码用MD5加密，然后和用户的密码进行比对
        if(!MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())){
            System.out.println(sysUser.getPassword());
            throw new GuiguException(201,"密码不正确");
        }
        //用户状态是否可用
        if(sysUser.getStatus() == 0){
            throw new GuiguException(201,"用户状态不可用");
        }

        //使用JWT创建token
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());

        Map<String, Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

//    {
//        "code": 20000,
//            "data": {
//        "roles": ["admin"],
//        "introduction": "I am a super administrator",
//         "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
//         "name": "Super Admin"
//    }
//    }


    //获取用户信息
    @GetMapping("/info")
    public Result info(HttpServletRequest request){

        //获取请求头token字符串
        String token = request.getHeader("token");
//        System.out.println(token+"-----------------------");
        //从token中获取用户名称(id)
        String username = JwtHelper.getUsername(token);
//        System.out.println(username);
        //根据用户名称获取用户信息（基本信息 菜单权限 和 按钮权限数据）
        Map<String,Object>map = sysUserService.getUserInfo(username);


//        Map<String, Object> map = new HashMap<>();
//        map.put( "roles","[admin]");
//        map.put("introduction", "I am a super administrator");
//        map.put( "avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
//        map.put("name", "Super Admin atguigu");
        return Result.ok(map);
    }



}
