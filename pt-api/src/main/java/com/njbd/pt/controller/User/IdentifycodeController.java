package com.njbd.pt.controller.User;


import com.njbd.pt.attribute.InterfaceAddressAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.attribute.RequestConstant;
import com.njbd.pt.model.UserInfo;
import com.njbd.pt.service.Com.IdentifycodeService;
import com.njbd.pt.utils.map.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by 李建成
 * on 15/9/9.
 */
@Controller
@RequestMapping(value = InterfaceAddressAttribute.ACCOUNT)
public class IdentifycodeController {

    @Autowired
    private IdentifycodeService identifycodeService;


    /**
     * 发送验证码
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.SENDVERCODE_ACTION)
    public Map<String, Object> sendVerifyCode(
            @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params
    ) {
        Map<String, Object> returnMap;
        Map requestMap = JsonUtil.json2map(params);
        try {
            String phone = requestMap.get(ParameterAttribute.PHONE).toString();
            Integer type = Integer.parseInt(requestMap.get(ParameterAttribute.TYPE).toString());
            returnMap = identifycodeService.getIdentifycode(phone, type);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    /**
     * 验证验证码
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.CHECKVERCODE_ACTION)
    public Map verifyCode(
            @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params
    ) {
        Map<String, Object> returnMap;
        Map requestMap = JsonUtil.json2map(params);
        try {
            String phone = requestMap.get(ParameterAttribute.PHONE).toString();
            String verifyCode = requestMap.get(ParameterAttribute.VERIFYCODE).toString();
            returnMap = identifycodeService.checkVerifyCode(phone, verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }
}
