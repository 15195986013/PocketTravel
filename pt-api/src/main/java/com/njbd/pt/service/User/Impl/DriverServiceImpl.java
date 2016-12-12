package com.njbd.pt.service.User.Impl;

import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.dao.DriverMapper;
import com.njbd.pt.model.Driver;
import com.njbd.pt.service.User.DriverService;
import com.njbd.pt.utils.string.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import static com.njbd.pt.attribute.RequestConstant.getRequestcodeDesc;


@Service
public class DriverServiceImpl implements DriverService {
    private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);

    @Autowired
    private DriverMapper driverMapper;

    public Map getDriverCertificateInfo(String user_id) {
        Map returnMap;
        logger.info("开始获取司机认证信息");
        Driver driver=null;
        try {
            driver=driverMapper.selectByUserId(user_id);
            if(driver!=null){
                returnMap = getRequestcodeDesc(0);
                if(driver.getState()==0){
                    //已认证
                }else if(driver.getState()==1){
                    //未认证
                }
            }else{
                returnMap = getRequestcodeDesc(100);
            }
            returnMap.put(ParameterAttribute.DATA,null);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA,null);
            return returnMap;
        }
        return returnMap;
    }

    public Map addDriver(String user_id, String name, String identityNumber, String drivingNumber, String vehicleNumber, String plateNumber, String units, String info) {
        Map returnMap;
        logger.info("开始增加认证司机");
        Driver driver=new Driver();
        driver.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
        driver.setUserId(user_id);
        driver.setName(name);
        driver.setState(1);
        driver.setIdentitynumber(identityNumber);
        driver.setDrivingNumber(drivingNumber);
        driver.setVehiclenumber(vehicleNumber);
        driver.setPlatenumber(plateNumber);
        driver.setUnits(units);
        driver.setInfo(info);
        driver.setCreateTime(new Date());
        try {
            int n=driverMapper.insertSelective(driver);
            if(n==1){
                returnMap = getRequestcodeDesc(0);
            }else{
                returnMap = getRequestcodeDesc(104);
            }
            returnMap.put(ParameterAttribute.DATA,null);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA,null);
            return returnMap;
        }
        return returnMap;
    }

}
