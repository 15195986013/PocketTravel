package com.njbd.pt.service.User;


import java.util.Map;

public interface UserService {
    //用户注册
    Map accountRegist(String phone,String password,String verCode,String clientVer);

    //用户登录
    Map accountLogin(String phone,String password);

    //重置密码
    Map resetPassword(String phone,String newPass,String verCode);

    //修改密码
    Map changePassword(String userId,String pass,String newPass);

}
