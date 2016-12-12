package com.njbd.pt.dao;

import com.njbd.pt.model.SystemInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemInfoMapper {
    int deleteByPrimaryKey(String pkId);

    int insert(SystemInfo record);

    int insertSelective(SystemInfo record);

    SystemInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SystemInfo record);

    int updateByPrimaryKey(SystemInfo record);
}