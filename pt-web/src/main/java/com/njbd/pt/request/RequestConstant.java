package com.njbd.pt.request;

import com.njbd.pt.attribute.ParameterAttribute;

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
            case -1:
                requestMap.put(ParameterAttribute.MSG, "获取用户权限失败!");
                break;
            case 0:
                requestMap.put(ParameterAttribute.MSG, "请求成功!");
                break;
            case 1:
                requestMap.put(ParameterAttribute.MSG, "数据不存在!");
                break;
            case 2:
                requestMap.put(ParameterAttribute.MSG, "暂无数据!");
                break;
            case 100:
                requestMap.put(ParameterAttribute.MSG, "系统内部错误!");
                break;
            case 107:
                requestMap.put(ParameterAttribute.MSG, "用户名或密码错误!");
                break;
            case 111:
                requestMap.put(ParameterAttribute.MSG, "用户已存在!");
                break;
            case 141:
                requestMap.put(ParameterAttribute.MSG, "对不起不能关闭超管用户");
                break;
            case 151:
                requestMap.put(ParameterAttribute.MSG, "对不起不能关闭超管权限");
                break;
            case 161:
                requestMap.put(ParameterAttribute.MSG, "id 不存在");
                break;
            case 171:
                requestMap.put(ParameterAttribute.MSG, "密码不正确!");
                break;
            case 181:
                requestMap.put(ParameterAttribute.MSG, "已经启用");
                break;
            case 191:
                requestMap.put(ParameterAttribute.MSG, "已经禁用!");
                break;
            default:
                break;
        }

        return requestMap;
    }


}
