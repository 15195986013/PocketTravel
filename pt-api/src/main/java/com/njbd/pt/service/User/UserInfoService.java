package com.njbd.pt.service.User;

import com.njbd.pt.dao.UserInfoMapper;
import com.njbd.pt.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserInfoService {
    //获取个人信息
    Map getUserInfo(String userId);

    //修改个人信息
    Map updateUserInfo(String nickName,String version,Integer sex,String avatar, String userId);


}
