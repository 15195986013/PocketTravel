package com.njbd.pt.controller.User;

import com.njbd.pt.attribute.InterfaceAddressAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.model.UserInfo;
import com.njbd.pt.service.Com.PostService;
import com.njbd.pt.service.User.DriverService;
import com.njbd.pt.service.User.UserInfoService;
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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = InterfaceAddressAttribute.PERSONAL)
public class PersonalController {
    private static final Logger logger = LoggerFactory.getLogger(PersonalController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private PostService postService;

    /**
     * 获取个人信息
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.USERINFO_ACTION, method = RequestMethod.POST)
    public Map getUserInfo(HttpServletResponse response,
                           @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params) {
        Map requestMap = JsonUtil.json2map(params);
        String userId = (String) requestMap.get(ParameterAttribute.USER_ID);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            response.setContentType("application/json;charset=UTF-8");
            returnMap = userInfoService.getUserInfo(userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《PersonalController getUserInfo》 error", e);
        }
        return returnMap;
    }

    /**
     * 修改个人信息
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.CHANGEUSERINFO_ACTION, method = RequestMethod.POST)
    public Map updateUserInfo(HttpServletResponse response,

                              @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params) {
        Map requestMap = JsonUtil.json2map(params);
        String userId = (String) requestMap.get(ParameterAttribute.USER_ID);
        String clientVer = (String) requestMap.get(ParameterAttribute.CLIENT_TYPE);
        String avatar = (String) requestMap.get(ParameterAttribute.AVATAR);
        String nickName = (String) requestMap.get(ParameterAttribute.NICKNAME);
        Integer sex = (Integer) requestMap.get(ParameterAttribute.SEX);
        UserInfo userInfo = new UserInfo();
        Map<String, Object> returnMap = new HashedMap();
        try {
            response.setContentType("application/json;charset=UTF-8");
            returnMap = userInfoService.updateUserInfo(nickName, clientVer, sex, avatar, userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《PersonalController updateUserInfo》 error", e);
        }
        return requestMap;
    }

    /**
     * 获取司机认证信息
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.CERTIFICATIONINFO_ACTION, method = RequestMethod.POST)
    public Map getDriverCertificateInfo(HttpServletResponse response,
                                        @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params) {
        Map requestMap = JsonUtil.json2map(params);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String userId = (String) requestMap.get(ParameterAttribute.USER_ID);
        try {
            response.setContentType("application/json;charset=UTF-8");
            returnMap = driverService.getDriverCertificateInfo(userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《PersonalController getDriverCertificateInfo》 error", e);
        }
        return returnMap;
    }

    /**
     * 新增司机
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.CERTIFICATION_ACTION, method = RequestMethod.POST)
    public Map addDriver(HttpServletResponse response,
                         @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params) {
        Map requestMap = JsonUtil.json2map(params);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String userId = (String) requestMap.get(ParameterAttribute.USER_ID);
        String name = (String) requestMap.get(ParameterAttribute.NAME);
        String identityNumber = (String) requestMap.get(ParameterAttribute.IDENTITYNUMBER);
        String drivingNumber = (String) requestMap.get(ParameterAttribute.DRIVINGNUMBER);
        String vehicleNumber = (String) requestMap.get(ParameterAttribute.VEHICLENUMBER);
        String plateNumber = (String) requestMap.get(ParameterAttribute.PLATENUMBER);
        String units = (String) requestMap.get(ParameterAttribute.UNITS);
        String info = (String) requestMap.get(ParameterAttribute.INFO);
        try {
            response.setContentType("application/json;charset=UTF-8");
            returnMap = driverService.addDriver(userId, name, identityNumber, drivingNumber, vehicleNumber, plateNumber, units, info);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《PersonalController addDriver》 error", e);
        }

        return returnMap;
    }

    /**
     * 获取乘载记录
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.GETRECORDLIST_ACTION, method = RequestMethod.POST)
    public Map getRecordList(HttpServletResponse response,
                             @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params) {
        Map<String, Object> returnMap = new HashedMap();
        try {
            response.setContentType("application/json;charset=UTF-8");
            Map requestMap = JsonUtil.json2map(params);
            String type = (String) requestMap.get(ParameterAttribute.TYPE);
            Integer startIndex = (Integer) requestMap.get(ParameterAttribute.STARTINDEX);
            Integer pageSize = (Integer) requestMap.get(ParameterAttribute.PAGESIZE);
            String time = (String) requestMap.get(ParameterAttribute.TIME);
            Map<String, Object> querymap = new HashMap<String, Object>();
            querymap.put(ParameterAttribute.TYPE, type);
            querymap.put(ParameterAttribute.PAGE_START, (startIndex - 1) * pageSize);
            querymap.put(ParameterAttribute.PAGE_END, startIndex * pageSize);
            String newTime = new SimpleDateFormat("yyyy").format(time);
            querymap.put(ParameterAttribute.TIME, "%" + newTime + "%");
            returnMap = postService.getTripList(querymap);

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《PersonalController getRecordList》 error", e);
        }
        return returnMap;
    }

}
