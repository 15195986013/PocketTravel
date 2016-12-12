package com.njbd.pt.dao;


import com.njbd.pt.model.Identifycode;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentifycodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Identifycode record);

    int insertSelective(Identifycode record);

    Identifycode selectByPrimaryKey(String id);

    Identifycode selectByPhone(String phone);

    int updateByPrimaryKeySelective(Identifycode record);

    int updateByPrimaryKey(Identifycode record);
}