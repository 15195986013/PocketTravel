package com.njbd.pt.service.User.Impl;

import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.attribute.RequestConstant;
import com.njbd.pt.dao.*;
import com.njbd.pt.model.*;
import com.njbd.pt.service.User.UserService;
import com.njbd.pt.utils.map.MapUtils;
import com.njbd.pt.utils.string.UUIDUtils;
import javafx.geometry.Pos;
import org.apache.commons.lang3.StringUtils;
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
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IdentifycodeMapper identifycodeMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private VersionMapper versionMapper;

    @Autowired
    private DriverMapper driverMapper;

    public Map accountRegist(String phone, String password,String verCode, String clientVer) {
        Map returnMap;
        try {
            Identifycode identifycode = identifycodeMapper.selectByPhone(phone);
            if (verCode == null) {
                returnMap = RequestConstant.getRequestcodeDesc(152);
                return returnMap;
            }
            if (!identifycode.getCode().trim().equals(verCode)) {
                returnMap = RequestConstant.getRequestcodeDesc(151);
                return returnMap;
            }
            UserInfo Olduser= userInfoMapper.selectByPhone(phone);
            if (Olduser!=null){
                returnMap = RequestConstant.getRequestcodeDesc(162);
                return returnMap;
            }



            //插入用户表
            User user=new User();
            user.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
            user.setAccount(phone);
            user.setPassword(password);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            int n1=userMapper.insertSelective(user);
            //插入用户信息表
            UserInfo userInfo=new UserInfo();
            userInfo.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
            userInfo.setUserId(user.getId());
            userInfo.setVersion(clientVer);
            userInfo.setPhone(phone);
            userInfo.setCreateTime(new Date());
            String uuid=UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()).toUpperCase().substring(1,7);
            userInfo.setNickname("用户"+uuid);
            int n2=userInfoMapper.insertSelective(userInfo);
            if(n1==1&&n2==1){
                returnMap = getRequestcodeDesc(0);
            }else{
                returnMap = getRequestcodeDesc(109);
            }
            returnMap.put(ParameterAttribute.DATA,null);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap =getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA,null);
        }
        return returnMap;

    }

    public Map accountLogin(String phone, String password) {
        Map returnMap =new HashMap();
        try {
            User user =null;
            if (StringUtils.isNotEmpty(phone)){
                user= userMapper.selectByAccount(phone);
                if (user==null){
                    returnMap=RequestConstant.getRequestcodeDesc(152);//手机号不存在
                    return returnMap;
                }
            }
            /**
             * 密码验证
             */
            if (password.indexOf(user.getPassword())<0){
                returnMap=RequestConstant.getRequestcodeDesc(130);//密码错误
                return returnMap;
            }else {
                UserInfo userInfo=userInfoMapper.selectByPrimaryUserId(user.getId());
                Map paramMap=new HashMap();
                paramMap.put(ParameterAttribute.USER_ID,userInfo.getUserId());
                paramMap.put(ParameterAttribute.NICKNAME,userInfo.getNickname());
                paramMap.put(ParameterAttribute.SEX,userInfo.getSex());
                paramMap.put(ParameterAttribute.PHONE,userInfo.getPhone());
                paramMap.put(ParameterAttribute.AVATAR,userInfo.getAvatar());
                /*是否为司机，是则返回认证状态*/
                Map queryMap=new HashMap();
                queryMap.put("userId",user.getId());
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnMap =getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA,null);
            return returnMap;
        }
        return returnMap;
    }

    public Map resetPassword(String phone, String newPass, String verCode) {
        Map returnMap;
        logger.info("重置密码开始：");
        try {
            Identifycode identifycode = identifycodeMapper.selectByPhone(phone);
            if (identifycode == null) {
                returnMap = RequestConstant.getRequestcodeDesc(152);
                return returnMap;
            }else if (!identifycode.getCode().trim().equals(verCode)) {
                returnMap = RequestConstant.getRequestcodeDesc(151);
                return returnMap;
            }else {
                User user =userMapper.selectByAccount(phone);
                int n=0;
                if(user!=null){
                    user.setPassword(newPass);
                    n=userMapper.updateByPrimaryKeySelective(user);
                }
                if(n==1){
                    returnMap = getRequestcodeDesc(0);
                }else{
                    returnMap = getRequestcodeDesc(111);
                }
                returnMap.put(ParameterAttribute.DATA,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnMap =getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA,null);
            return returnMap;
        }
        return returnMap;
    }

    @Override
    public Map changePassword(String userId, String pass, String newPass) {
        Map returnMap=new HashMap();
        logger.info("修改密码开始：");
        try {
            User user=userMapper.selectByPrimaryKey(userId);
            if(user==null){
                returnMap =getRequestcodeDesc(108);
                returnMap.put(ParameterAttribute.DATA,null);
                return returnMap;
            }
            if(!user.getPassword().equals(pass)){
                returnMap =getRequestcodeDesc(130);
                returnMap.put(ParameterAttribute.DATA,null);
                return returnMap;
            }
            user.setPassword(newPass);
           int n= userMapper.updateByPrimaryKeySelective(user);
            if(n==1){
                returnMap = getRequestcodeDesc(0);
            }else{
                returnMap = getRequestcodeDesc(164);
            }
            returnMap.put(ParameterAttribute.DATA,null);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap =getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA,null);
            return returnMap;
        }
        return returnMap;
    }

}
