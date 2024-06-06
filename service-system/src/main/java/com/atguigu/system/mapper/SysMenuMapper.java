package com.atguigu.system.mapper;

import com.atguigu.model.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2024-03-20
 */

//需要注册
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    //根据userId查询菜单信息
    List<SysMenu> findListByUserId(@Param("userId")String id);
}
