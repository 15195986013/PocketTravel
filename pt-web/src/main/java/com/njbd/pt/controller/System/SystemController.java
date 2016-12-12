package com.njbd.pt.controller.System;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.njbd.pt.attribute.HttpAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.attribute.ViewPrefixAttribute;
import com.njbd.pt.model.SystemInfo;
import com.njbd.pt.service.System.SystemService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李建成
 * on 2016/11/28.
 */
@Controller
@RequestMapping(value = HttpAttribute.ADMIN_HTML)
public class SystemController {
    @Autowired
    private SystemService systemService;


    @RequestMapping(value = HttpAttribute.LOGIN_HTML)
    public ModelAndView Login() {
        Map LoginData = systemService.selectSystemInfo();
        return new ModelAndView(ViewPrefixAttribute.LOGIN_PREFIX, LoginData);
    }


    @RequestMapping(value = HttpAttribute.INDEX_HTML)
    public ModelAndView AdminIndex() {
        return new ModelAndView(ViewPrefixAttribute.INDEX_PREFIX);
    }


    /**
     * 加载主页信息
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = HttpAttribute.INDEX_SYSTEMINFO_JSON)
    public void IndexSystemInfo(
            HttpServletResponse response
    ) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter outWriter = response.getWriter();
        Map<String, Object> returnMap = systemService.selectSystemInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        outWriter.write(objectMapper.writeValueAsString(returnMap));
    }

    @ResponseBody
    @RequestMapping(value = HttpAttribute.SAVE_SYSTEMINFO_JSON)
    public Map<String, Object> saveSystemInfo(SystemInfo systemInfo) throws Exception {
        systemInfo.setUpdateTime(new Date());
        Map<String, Object> returnMap = systemService.saveSystemInfo(systemInfo);
        return returnMap;
    }

    /**
     * 登出
     * @param session
     * @return
     */
    @RequestMapping(value = HttpAttribute.LOGIN_OUT_HTML)
    public ModelAndView AdminLogout(HttpSession session) {
        session.removeAttribute(ParameterAttribute.ADMIN_ID);
        session.removeAttribute(ParameterAttribute.ACCOUNT);
        Map<String, Object> returnMap = new HashedMap();
        ModelAndView modelAndView = null;
        try {
            modelAndView = Login();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    @RequestMapping(value = HttpAttribute.SYSTEMMANAGER_HTML)
    public ModelAndView systemmanager()throws Exception{
        return new ModelAndView(ViewPrefixAttribute.SYSTEM_MANAGER_PREFIX);
    }

    @ResponseBody
    @RequestMapping(value = "/selectSystemInfo.json")
    public Map<String,Object> selectSystemInfo()throws Exception{
        Map<String,Object> returnMap = new HashMap<String, Object>();
        returnMap = systemService.selectSystemInfo();
        return returnMap;
    }



}
