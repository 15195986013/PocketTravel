package com.njbd.pt.service.System.Impl;

import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.dao.SystemInfoMapper;
import com.njbd.pt.model.SystemInfo;
import com.njbd.pt.request.RequestConstant;
import com.njbd.pt.service.System.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李建成
 * on 2016/11/28.
 */
@Service
public class SystemServiceImpl implements SystemService {

    private final static Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private SystemInfoMapper systemInfoMapper;

    @Override
    public Map selectSystemInfo() {
        Map returnMap;
        try {
            SystemInfo systemInfo = systemInfoMapper.selectByPrimaryKey("1");
            returnMap = RequestConstant.getRequestcodeDesc(0);
            returnMap.put(ParameterAttribute.SYSINFO, systemInfo);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取系统信息失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map saveSystemInfo(SystemInfo systemInfo) {
        Map returnMap;
        try {
            if (systemInfo.getId() == null) {
                systemInfo.setId("1");
            }
            systemInfoMapper.updateByPrimaryKeySelective(systemInfo);
            returnMap = RequestConstant.getRequestcodeDesc(0);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map indexSystemInfo() {
        Map returnMap = new HashMap();
        try {
            SystemInfo systemInfo = systemInfoMapper.selectByPrimaryKey("1");
            HashMap<String, Object> remap = new HashMap<String, Object>();
            remap.put(ParameterAttribute.TECHSUPPORT, systemInfo.getTechSupport());
            remap.put(ParameterAttribute.SYSVER, systemInfo.getSysVer());
            remap.put(ParameterAttribute.SYSNAME, systemInfo.getSysName());
            remap.put(ParameterAttribute.CONTACT, systemInfo.getContact());
            returnMap.put(ParameterAttribute.SYSTEM_DATA, remap);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("主页获取系统信息失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }
}
