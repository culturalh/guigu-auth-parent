package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysOperLog;
import com.atguigu.model.vo.SysOperLogQueryVo;
import com.atguigu.system.mapper.OperLogMapper;
import com.atguigu.system.service.OperLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperLogServiceImpl implements OperLogService {

    @Autowired
    private OperLogMapper  operLogMapper;

    @Override
    public void saveOperLog(SysOperLog sysOperLog) {
        operLogMapper.insert(sysOperLog);
    }

    @Override
    public IPage<SysOperLog> selectPage(Page<SysOperLog> pageParam, SysOperLogQueryVo sysOperLogQueryVo) {

//        	<where>
//			<if test="vo.title != null and vo.title != ''">
//                and title like CONCAT('%',#{vo.title},'%')
//			</if>
//			<if test="vo.operName != null and vo.operName != ''">
//                and oper_name like CONCAT('%',#{vo.operName},'%')
//			</if>
//	       <if test="vo.createTimeBegin != null and vo.createTimeBegin != ''">
//                and create_time >= #{vo.createTimeBegin}
//		   </if>
//		   <if test="vo.createTimeEnd != null and vo.createTimeEnd != ''">
//                and create_time &lt;= #{vo.createTimeEnd}
//		   </if>
//        and is_deleted = 0
//                </where>

        QueryWrapper<SysOperLog> wrapper = new QueryWrapper<>();
        if(sysOperLogQueryVo.getTitle() != null && sysOperLogQueryVo.getTitle() != ""){
            wrapper.like("title",sysOperLogQueryVo.getTitle());
        }
        if(sysOperLogQueryVo.getOperName() != null && sysOperLogQueryVo.getOperName() != ""){
            wrapper.like("oper_name",sysOperLogQueryVo.getOperName());
        }
        if(sysOperLogQueryVo.getCreateTimeBegin() != null && sysOperLogQueryVo.getCreateTimeBegin() != ""){
            wrapper.ge("create_time",sysOperLogQueryVo.getCreateTimeBegin());
        }
        if(sysOperLogQueryVo.getCreateTimeEnd() != null && sysOperLogQueryVo.getCreateTimeEnd() != ""){
            wrapper.le("create_time",sysOperLogQueryVo.getCreateTimeEnd());
        }
        return  operLogMapper.selectPage(pageParam,wrapper);
    }
}
