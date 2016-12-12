package com.njbd.pt.dao;

import com.njbd.pt.model.Driver;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DriverMapper {
    int deleteByPrimaryKey(String id);

    int insert(Driver record);

    int insertSelective(Driver record);

    Driver selectByPrimaryKey(String id);

    Driver selectByUserId(String user_id);

    int updateByPrimaryKeySelective(Driver record);

    int updateByPrimaryKey(Driver record);


    List<Driver> selectByPage(Map<String, Object> querymap);
}