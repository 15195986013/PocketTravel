package com.njbd.pt.dao;


import com.njbd.pt.model.SendmsgCount;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SendmsgCountMapper {

    int insertSelective(SendmsgCount sendmsgCount);

    SendmsgCount selectById(String id);

    SendmsgCount selectByPhone(String phone);

    void updateMsgCountByID(SendmsgCount sendmsgCount);

    void deleteMsgCountByID(String id);

    List<SendmsgCount> selectByPage(Map<String, Object> map);

    List selectServerByPage(Map<String, Object> map);

    int selectServerByPageCount(Map<String, Object> map);
}