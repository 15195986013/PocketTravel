package com.njbd.pt.service.User.Impl;

import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.dao.DriverMapper;
import com.njbd.pt.dao.UserInfoMapper;
import com.njbd.pt.model.Driver;
import com.njbd.pt.model.UserInfo;
import com.njbd.pt.request.RequestConstant;
import com.njbd.pt.service.User.DriverService;
import com.njbd.pt.utils.map.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 李建成
 *
 * @date 2016/12/5-11:25.on NJBD
 */
@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private DriverMapper driverMapper;

    @Override
    public Map<String, Object> getAllDriver(Map<String, Object> querymap) {

        Map returnMap;
        List<Driver> drivers = driverMapper.selectByPage(querymap);
        List<Object> ReturnList = new ArrayList<Object>();
        for (Driver driver : drivers) {
            Map paramMap = MapUtils.BeanToMap(driver);
            String UserName = "";
            if (StringUtils.isNotEmpty(driver.getUserId())) {
                UserInfo userInfo = userInfoMapper.selectByPrimaryUserId(driver.getUserId());
                UserName = userInfo == null || "".equals(userInfo.getNickname()) ? "<font color='red'><s>用户已删除</s></font>" : userInfo.getNickname();
            }
            paramMap.put(ParameterAttribute.NAME, UserName == null || "".equals(UserName) ? "" : UserName);
            ReturnList.add(paramMap);
        }
        returnMap = RequestConstant.getRequestcodeDesc(0);
        MapUtils.MapRemovePageSize(returnMap, querymap, ReturnList);
        List<Driver> list = driverMapper.selectByPage(querymap);
        returnMap.put(ParameterAttribute.PAGE_TOTAl, list.size());
        return returnMap;
    }

    @Override
    public Map<String, Object> changeDriverStatus(String DriverId) {
        Map returnMap = new HashedMap();
        Driver driver = driverMapper.selectByPrimaryKey(DriverId);
        if (driver.getState() == 1) {
            driver.setState(0);
            returnMap.put(ParameterAttribute.CODE, 0);
            returnMap.put(ParameterAttribute.MSG, "取消审核");
        } else {
            driver.setState(1);
            returnMap.put(ParameterAttribute.CODE, 0);
            returnMap.put(ParameterAttribute.MSG, "已审核");
        }
        driver.setUpdateTime(new Date());
        driverMapper.updateByPrimaryKey(driver);
        return returnMap;
    }
}
