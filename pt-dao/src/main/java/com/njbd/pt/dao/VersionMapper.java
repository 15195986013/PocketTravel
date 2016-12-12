package com.njbd.pt.dao;

import com.njbd.pt.model.Version;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VersionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Version record);

    int insertSelective(Version record);

    Version selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Version record);

    int updateByPrimaryKey(Version record);

    List<Version> selectByPage(Map<String, Object> querymap);
}