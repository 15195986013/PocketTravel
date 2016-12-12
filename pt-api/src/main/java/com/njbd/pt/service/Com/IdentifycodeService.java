package com.njbd.pt.service.Com;

import java.util.Map;

/**
 * Created by  李建成
 * on 12 06
 */
public interface IdentifycodeService {
    //获取验证码
    Map getIdentifycode(String phone, Integer type);

    //验证码验证
    Map checkVerifyCode(String phone, String verifyCode);
}
