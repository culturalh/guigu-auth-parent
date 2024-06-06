package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysLoginLog;
import com.atguigu.model.vo.SysLoginLogQueryVo;
import com.atguigu.system.mapper.LoginLogMapper;
import com.atguigu.system.service.LoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    //依赖注入
    @Autowired
    private LoginLogMapper loginLogMapper;

    //记录登入日志信息
    @Override
    public void recordLoginLog(String username, Integer status, String ipaddr, String message) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setStatus(status);
        sysLoginLog.setIpaddr(ipaddr);
        sysLoginLog.setMsg(message);
        loginLogMapper.insert(sysLoginLog);
    }

    @Override
    public IPage<SysLoginLog> selectPage(Page<SysLoginLog> pageParam, SysLoginLogQueryVo sysLoginLogQueryVo) {
//        		<where>
//	       <if test="vo.username != null and vo.username != ''">
//                and username = #{vo.username}
//			</if>
//	       <if test="vo.createTimeBegin != null and vo.createTimeBegin != ''">
//                and create_time >= #{vo.createTimeBegin}
//		   </if>
//		   <if test="vo.createTimeEnd != null and vo.createTimeEnd != ''">
//                and create_time &lt;= #{vo.createTimeEnd}
//		   </if>
//        and is_deleted = 0
//                </where>
        QueryWrapper<SysLoginLog> wrapper = new QueryWrapper<>();
        if(sysLoginLogQueryVo.getUsername() != null && sysLoginLogQueryVo.getUsername() !=""){
            wrapper.eq("username",sysLoginLogQueryVo.getUsername());
        }
        if(sysLoginLogQueryVo.getCreateTimeBegin() != null && sysLoginLogQueryVo.getCreateTimeBegin() != ""){
            wrapper.ge("create_time",sysLoginLogQueryVo.getCreateTimeBegin());
        }

        if(sysLoginLogQueryVo.getCreateTimeEnd() != null && sysLoginLogQueryVo.getCreateTimeEnd() != ""){
            wrapper.le("create_time",sysLoginLogQueryVo.getCreateTimeEnd());
        }

       return loginLogMapper.selectPage(pageParam,wrapper);


    }
}
