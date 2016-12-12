package com.njbd.pt.dao;

import com.njbd.pt.model.AppFeeback;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AppFeebackMapper {
    int deleteByPrimaryKey(String id);

    int insert(AppFeeback record);

    int insertSelective(AppFeeback record);

    AppFeeback selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AppFeeback record);

    int updateByPrimaryKey(AppFeeback record);

    List<AppFeeback> selectByPage(Map<String, Object> querymap);

    List<Map> selectFeedBacks(Map querymap);

}