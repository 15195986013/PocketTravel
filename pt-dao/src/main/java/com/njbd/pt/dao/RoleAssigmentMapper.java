package com.njbd.pt.dao;


import com.njbd.pt.model.RoleAssigment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleAssigmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(RoleAssigment record);

    int insertSelective(RoleAssigment record);

    RoleAssigment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoleAssigment record);

    int updateByPrimaryKey(RoleAssigment record);

    List<RoleAssigment> selectByPage(Map roleMap);

    int deleteByMap(Map map);
}