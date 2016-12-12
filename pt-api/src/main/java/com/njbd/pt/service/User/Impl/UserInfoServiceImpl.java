package com.njbd.pt.service.User.Impl;

import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.dao.DriverMapper;
import com.njbd.pt.dao.UserInfoMapper;
import com.njbd.pt.model.Driver;
import com.njbd.pt.model.UserInfo;
import com.njbd.pt.service.User.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.njbd.pt.attribute.RequestConstant.getRequestcodeDesc;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private DriverMapper driverMapper;

    public Map getUserInfo(String userId) {
        Map returnMap;
        UserInfo userInfo= null;
        try {
            userInfo=userInfoMapper.selectByPrimaryUserId(userId);
            if(userInfo!=null){
                returnMap = getRequestcodeDesc(0);
            }else{
                returnMap = getRequestcodeDesc(102);
            }
            Map paramMap=new HashMap();
            paramMap.put(ParameterAttribute.USER_ID,userInfo.getUserId());
            paramMap.put(ParameterAttribute.NICKNAME,userInfo.getNickname());
            paramMap.put(ParameterAttribute.SEX,userInfo.getSex());
            paramMap.put(ParameterAttribute.PHONE,userInfo.getPhone());
            paramMap.put(ParameterAttribute.AVATAR,userInfo.getAvatar());
            paramMap.put(ParameterAttribute.IDENTITY,userInfo.getIdentity());
            Map queryMap=new HashMap();
            Driver drivers=driverMapper.selectByUserId(userId);
            if (drivers==null){
                paramMap.put(ParameterAttribute.STATE,0);
            }else {
                if (drivers.getState()==1){
                    paramMap.put(ParameterAttribute.STATE,1);
                }
                if (drivers.getState()==2){
                    paramMap.put(ParameterAttribute.STATE,2);
                }
                if (drivers.getState()==3){
                    paramMap.put(ParameterAttribute.STATE,3);
                }
            }
            returnMap.put(ParameterAttribute.DATA,paramMap);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA,null);
            return returnMap;
        }
        return returnMap;
    }

    public Map updateUserInfo(String nickName,String version,Integer sex,String avatar, String userId) {
        Map returnMap;
        UserInfo userInfo=null;
        try {
            userInfo =userInfoMapper.selectByPrimaryUserId(userId);
            int n=0;
            if(userInfo!=null){
                userInfo.setNickname(nickName);
                userInfo.setSex(sex);
                userInfo.setAvatar(avatar);
                userInfo.setVersion(version);
                userInfo.setUpdateTime(new Date());
                n=userInfoMapper.updateByPrimaryKeySelective(userInfo);
            }
            if(n==1){
                returnMap =getRequestcodeDesc(0);
            }else{
                returnMap =getRequestcodeDesc(103);
            }
            Map paramMap=new HashMap();
            paramMap.put(ParameterAttribute.USER_ID,userInfo.getUserId());
            paramMap.put(ParameterAttribute.NICKNAME,userInfo.getNickname());
            paramMap.put(ParameterAttribute.SEX,userInfo.getSex());
            paramMap.put(ParameterAttribute.PHONE,userInfo.getPhone());
            paramMap.put(ParameterAttribute.AVATAR,userInfo.getAvatar());
            paramMap.put(ParameterAttribute.IDENTITY,userInfo.getIdentity());

            Map queryMap=new HashMap();
            queryMap.put("userId",userId);
            List<Driver> drivers=driverMapper.selectByPage(queryMap);
            if(drivers.size()<=0||drivers==null){
                paramMap.put(ParameterAttribute.STATE,0);
            }
            if(drivers.size()>0){
                if(drivers.get(0).getState()==1){
                    paramMap.put(ParameterAttribute.STATE,1);
                }
                if(drivers.get(0).getState()==2){
                    paramMap.put(ParameterAttribute.STATE,2);
                }
                if(drivers.get(0).getState()==3){
                    paramMap.put(ParameterAttribute.STATE,3);
                }
            }
            returnMap.put(ParameterAttribute.DATA,paramMap);
        }catch (Exception e) {
            e.printStackTrace();
            returnMap =getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA,null);
            return returnMap;
        }
        return returnMap;
    }


}
