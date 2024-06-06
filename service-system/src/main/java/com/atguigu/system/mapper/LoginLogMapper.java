package com.atguigu.system.mapper;

import com.atguigu.model.system.SysLoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface LoginLogMapper extends BaseMapper<SysLoginLog> {
}
