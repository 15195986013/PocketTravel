package com.njbd.pt.controller.User;

import com.njbd.pt.attribute.InterfaceAddressAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.service.User.UserService;
import com.njbd.pt.utils.map.JsonUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = InterfaceAddressAttribute.ACCOUNT)
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.REGISTER_ACTION, method = RequestMethod.POST)
    public Map accountRegist(HttpServletResponse response,
                             @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params) {
        Map requestMap = JsonUtil.json2map(params);
        String phone = (String) requestMap.get(ParameterAttribute.PHONE);
        String password = (String) requestMap.get(ParameterAttribute.PASSWORD);
        String verCode = (String) requestMap.get(ParameterAttribute.VERIFYCODE);
        String clientVer = (String) requestMap.get(ParameterAttribute.CLIENT_TYPE);

        Map<String, Object> returnMap = new HashedMap();
        try {
            response.setContentType("application/json;charset=UTF-8");
            returnMap = userService.accountRegist(phone, password, verCode, clientVer);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《AccountController accountRegist》 error", e);
        }
        return returnMap;
    }

    /**
     * 用户登录
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.LOGIN_ACTION, method = RequestMethod.POST)
    public Map accountLogin(HttpServletResponse response,

                            @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params
    ) {
        System.out.println(params);
        Map<String, Object> returnMap = new HashedMap();
        try {
            response.setContentType("application/json;charset=UTF-8");
            Map requestMap = JsonUtil.json2map(params);
            String phone = (String) requestMap.get(ParameterAttribute.PHONE);
            String password = (String) requestMap.get(ParameterAttribute.PASSWORD);
            returnMap = userService.accountLogin(phone, password);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《AccountController accountLogin》 error", e);
        }
        return returnMap;
    }

    /**
     * 重置密码
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.RESETPASSWORD_ACTION, method = RequestMethod.POST)
    public Map resetPassword(HttpServletResponse response,
                             @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params
    ) {
        Map<String, Object> returnMap = new HashedMap();
        try {
            response.setContentType("application/json;charset=UTF-8");
            Map requestMap = JsonUtil.json2map(params);
            String phone = (String) requestMap.get(ParameterAttribute.PHONE);
            String verCode = (String) requestMap.get(ParameterAttribute.VERIFYCODE);
            String newPass = (String) requestMap.get(ParameterAttribute.NEWPASS);
            returnMap = userService.resetPassword(phone, newPass, verCode);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《AccountController resetPassword》 error", e);
        }

        return returnMap;
    }

    /***
     * 修改密码
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.CHANGEPASSWORD_ACTION, method = RequestMethod.POST)
    public Map changePassword(HttpServletResponse response,
                             @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params
    ) {
        Map<String, Object> returnMap = new HashedMap();
        try {
            response.setContentType("application/json;charset=UTF-8");
            Map requestMap = JsonUtil.json2map(params);
            String userId=(String) requestMap.get(ParameterAttribute.USER_ID);
            String pass = (String) requestMap.get(ParameterAttribute.PASS);
            String newPass = (String) requestMap.get(ParameterAttribute.NEWPASS);
            returnMap = userService.changePassword(userId,pass,newPass);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《AccountController resetPassword》 error", e);
        }
        return returnMap;
    }

}
