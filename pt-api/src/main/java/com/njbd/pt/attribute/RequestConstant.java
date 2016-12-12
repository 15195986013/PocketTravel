package com.njbd.pt.attribute;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李建成
 * ON:  2016/9/17 16:24
 */
public class RequestConstant {

    public static Map getRequestcodeDesc(Integer requestCode) {
        Map requestMap = new HashMap();

        requestMap.put(ParameterAttribute.CODE, requestCode);
        switch (requestCode) {
            case 1001:
                requestMap.put(ParameterAttribute.MSG, "系统内部错误!");
                break;
            case 0:
                requestMap.put(ParameterAttribute.MSG, "请求成功!");
                break;
            case 1:
                requestMap.put(ParameterAttribute.MSG, "请求失败!");
                break;
            case 100:
                requestMap.put(ParameterAttribute.MSG, "系统错误，请求失败!");
                break;
            case 101:
                requestMap.put(ParameterAttribute.MSG, "登录失败!");
                break;
            case 102:
                requestMap.put(ParameterAttribute.MSG, "获取用户信息失败!");
                break;
            case 103:
                requestMap.put(ParameterAttribute.MSG, "修改个人信息失败!");
                break;
            case 104:
                requestMap.put(ParameterAttribute.MSG, "新增认证司机失败!");
                break;
            case 105:
                requestMap.put(ParameterAttribute.MSG, "网约车发布失败!");
                break;
            case 106:
                requestMap.put(ParameterAttribute.MSG, "取消网约车发布失败!");
                break;
            case 107:
                requestMap.put(ParameterAttribute.MSG, "抢单失败!");
                break;
            case 108:
                requestMap.put(ParameterAttribute.MSG, "暂无数据");
                break;
            case 109:
                requestMap.put(ParameterAttribute.MSG, "注册失败!");
                break;
            case 110:
                requestMap.put(ParameterAttribute.MSG, "登录失败!");
                break;
            case 111:
                requestMap.put(ParameterAttribute.MSG, "重置密码失败!");
                break;
            case 112:
                requestMap.put(ParameterAttribute.MSG, "获取乘载记录失败!");
                break;
            case 130:
                requestMap.put(ParameterAttribute.MSG, "密码错误!");
                break;
            case 143:
                requestMap.put(ParameterAttribute.MSG, "今日发送超限!");
                break;
            case 141:
                requestMap.put(ParameterAttribute.MSG, "稍后再试!");
                break;
            case 151:
                requestMap.put(ParameterAttribute.MSG, "验证码不正确!");
                break;
            case 152:
                requestMap.put(ParameterAttribute.MSG, "手机号不存在!");
                break;
            case 162:
                requestMap.put(ParameterAttribute.MSG, "已注册请登录!");
                break;
            case 163:
                requestMap.put(ParameterAttribute.MSG, "用户信息不存在");
                break;
            case 164:
                requestMap.put(ParameterAttribute.MSG, "修改失败");
                break;


        }

        return requestMap;
    }


}
