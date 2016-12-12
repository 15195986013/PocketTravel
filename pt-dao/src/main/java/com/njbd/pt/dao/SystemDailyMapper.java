package com.njbd.pt.dao;

import com.njbd.pt.model.SystemDaily;

public interface SystemDailyMapper {
    int deleteByPrimaryKey(String id);

    int insert(SystemDaily record);

    int insertSelective(SystemDaily record);

    SystemDaily selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SystemDaily record);

    int updateByPrimaryKey(SystemDaily record);
}