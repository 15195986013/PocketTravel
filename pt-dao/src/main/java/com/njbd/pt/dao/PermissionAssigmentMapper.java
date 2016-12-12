package com.njbd.pt.dao;

import com.njbd.pt.model.PermissionAssigment;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface PermissionAssigmentMapper {
    int insert(PermissionAssigment record);

    int insertSelective(PermissionAssigment record);

    List<PermissionAssigment> selectByPage(HashMap<String, Object> permissionAssigmentMap);

    int deleteByPrimaryKey(String id);


}