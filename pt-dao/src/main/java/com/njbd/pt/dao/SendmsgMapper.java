package com.njbd.pt.dao;


import com.njbd.pt.model.Sendmsg;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SendmsgMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(Sendmsg record);

    Sendmsg selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Sendmsg record);

    List<Sendmsg> searchSendmsgList();

    List<Sendmsg> searchSendmsgListByPage(Map queryMap);

    List<Sendmsg> searchSendmsgListByPageCount(Map queryMap);
}