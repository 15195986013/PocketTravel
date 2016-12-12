package com.njbd.pt.service.Com.Impl;


import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.attribute.RequestConstant;
import com.njbd.pt.dao.*;
import com.njbd.pt.model.Identifycode;
import com.njbd.pt.model.Sendmsg;
import com.njbd.pt.model.SendmsgCount;
import com.njbd.pt.model.UserInfo;
import com.njbd.pt.service.Com.IdentifycodeService;
import com.njbd.pt.utils.date.DataUtil;
import com.njbd.pt.utils.date.VcodeUtil;
import com.njbd.pt.utils.msg.SendMSG;
import com.njbd.pt.utils.string.UUIDUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by李建成
 * on 16/12/6.
 */
@Service
public class IdentifycodeServiceImpl implements IdentifycodeService {

    @Autowired
    private IdentifycodeMapper identifycodeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private SendmsgMapper sendmsgMapper;
    @Autowired
    private SendmsgCountMapper sendmsgCountMapper;

    /**
     * 获取验证码
     * @param phone
     * @param type  验证码类型
     * @return
     */

    @Override
    public Map getIdentifycode(String phone, Integer type) {
        Map returnMap = new HashMap();
        try {
            if (type == 1) {//（ 1:重置密码 &修改手机号    2:注册）
                String msg = "";//短信验证码
                Identifycode identifycode = identifycodeMapper.selectByPhone(phone);
                if (identifycode != null) {
//                    Long datediff = DataUtil.timeDiffer(new Date(), identifycode.getCreateTime());
//                    if (datediff < 60 * 10) {
//                        returnMap = RequestConstant.getRequestcodeDesc(151);
//                        return returnMap;
//                    } else {

                        //添加短信发送的次数
                        Map reqMap = new HashMap();
                        reqMap.put(ParameterAttribute.PHONE, phone);
                        reqMap.put(ParameterAttribute.CREATETIME, "%" + DataUtil.getYearDate() + "%");
                        List<SendmsgCount> sendmsgCountList = sendmsgCountMapper.selectByPage(reqMap);
                        if (sendmsgCountList.size() > 0) {//是否今天发过
                            SendmsgCount sendmsgCount = sendmsgCountList.get(0);
                            if (Integer.valueOf(sendmsgCount.getTotalnum()) >= Integer.valueOf(ParameterAttribute.MSG_COUNT)) {//今天发送是否超过5条
                                returnMap = RequestConstant.getRequestcodeDesc(143);
                                return returnMap;
                            } else {

                                identifycode.setCode(VcodeUtil.generateWord());
                                identifycode.setCreateTime(new Date());
                                identifycodeMapper.updateByPrimaryKeySelective(identifycode);
                                msg = ParameterAttribute.REGISTER + identifycode.getCode() + ",有效期为10分钟";

                                sendmsgCount.setTotalnum(String.valueOf(Integer.valueOf(sendmsgCount.getTotalnum()) + 1));
                                sendmsgCount.setCreatetime(new Date());
                                sendmsgCountMapper.updateMsgCountByID(sendmsgCount);
                            }
                        } else {
                            identifycode.setCode(VcodeUtil.generateWord());
                            identifycode.setCreateTime(new Date());
                            identifycodeMapper.updateByPrimaryKeySelective(identifycode);
                            msg = ParameterAttribute.REGISTER + identifycode.getCode() + ",有效期为10分钟";

                            SendmsgCount sendmsgCount = new SendmsgCount();
                            sendmsgCount.setId(UUID.randomUUID().toString());
                            sendmsgCount.setPhone(phone);
                            sendmsgCount.setTotalnum("1");
                            sendmsgCount.setCreatetime(new Date());
                            sendmsgCountMapper.insertSelective(sendmsgCount);
                        }
//                    }
                } else {
                    Identifycode sidentifycode = new Identifycode();
                    sidentifycode.setId(UUID.randomUUID().toString());
                    sidentifycode.setPhone(phone);
//                    sidentifycode.setCode(VcodeUtil.generateWord());
                     sidentifycode.setCode("666666");
                    sidentifycode.setCreateTime(new Date());
                    identifycodeMapper.insertSelective(sidentifycode);
                    msg = ParameterAttribute.REGISTER + sidentifycode.getCode() + ",有效期为10分钟";

                    //添加短信发送的次数
                    Map reqMap = new HashMap();
                    reqMap.put(ParameterAttribute.PHONE, phone);
                    reqMap.put(ParameterAttribute.CREATETIME, "%" + DataUtil.getYearDate() + "%");
                    List<SendmsgCount> sendmsgCountList = sendmsgCountMapper.selectByPage(reqMap);
                    if (sendmsgCountList.size() > 0) {//是否今天发过
                        SendmsgCount sendmsgCount = sendmsgCountList.get(0);
                        if (Integer.valueOf(sendmsgCount.getTotalnum()) >= Integer.valueOf(ParameterAttribute.MSG_COUNT)) {//今天发送是否超过5条
                            returnMap = RequestConstant.getRequestcodeDesc(143);
                            return returnMap;
                        } else {
                            sendmsgCount.setTotalnum(String.valueOf(Integer.valueOf(sendmsgCount.getTotalnum()) + 1));
                            sendmsgCount.setCreatetime(new Date());
                            sendmsgCountMapper.updateMsgCountByID(sendmsgCount);
                        }
                    } else {
                        SendmsgCount sendmsgCount = new SendmsgCount();
                        sendmsgCount.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
                        sendmsgCount.setPhone(phone);
                        sendmsgCount.setTotalnum("1");
                        sendmsgCount.setCreatetime(new Date());
                        sendmsgCountMapper.insertSelective(sendmsgCount);
                    }
                }

//                int flag = SendMSG.sendMsg(msg, phone);

                 int flag = 1;

                if (flag > 0) {
                    //添加发短信的记录
                    Sendmsg sendmsg = new Sendmsg();
                    sendmsg.setId(UUID.randomUUID().toString());
                    sendmsg.setContent(msg);
                    sendmsg.setPhone(phone);
                    sendmsg.setType("1");//被发送的类型（0注册，1忘记密码）
                    sendmsg.setCreatetime(new Date());
                    sendmsgMapper.insertSelective(sendmsg);

                    returnMap = RequestConstant.getRequestcodeDesc(0);
                    return returnMap;
                }
            }

            if (type == 2) {
                UserInfo userInfo = userInfoMapper.selectByPhone(phone);
                String msg = "";//短信验证码
                if (userInfo != null) {
                    returnMap = RequestConstant.getRequestcodeDesc(162);
                    return returnMap;
                } else {

                    Identifycode identifycode = identifycodeMapper.selectByPhone(phone);
                    if (identifycode != null) {
//                    Long datediff = DataUtil.timeDiffer(new Date(), identifycode.getCreateTime());
//                    if (datediff < 60 * 10) {
//                        returnMap = RequestConstant.getRequestcodeDesc(101);
//                        return returnMap;
//                    } else {


                        //添加短信发送的次数
                        Map reqMap = new HashMap();
                        reqMap.put(ParameterAttribute.PHONE, phone);
                        reqMap.put(ParameterAttribute.CREATETIME, "%" + DataUtil.getYearDate() + "%");
                        List<SendmsgCount> sendmsgCountList = sendmsgCountMapper.selectByPage(reqMap);
                        if (sendmsgCountList.size() > 0) {//是否今天发过
                            SendmsgCount sendmsgCount = sendmsgCountList.get(0);
                            if (Integer.valueOf(sendmsgCount.getTotalnum()) >= Integer.valueOf(ParameterAttribute.MSG_COUNT)) {//今天发送是否超过5条
                                returnMap = RequestConstant.getRequestcodeDesc(143);
                                return returnMap;
                            } else {
//                                identifycode.setCode(VcodeUtil.generateWord());
                                identifycode.setCode("666666");
                                identifycode.setCreateTime(new Date());
                                identifycodeMapper.updateByPrimaryKeySelective(identifycode);
                                msg = ParameterAttribute.FORGET_PASS + identifycode.getCode() + ",有效期为10分钟";

                                sendmsgCount.setTotalnum(String.valueOf(Integer.valueOf(sendmsgCount.getTotalnum()) + 1));
                                sendmsgCount.setCreatetime(new Date());
                                sendmsgCountMapper.updateMsgCountByID(sendmsgCount);
                            }
                        } else {

                            identifycode.setCode(VcodeUtil.generateWord());
                            identifycode.setCreateTime(new Date());
                            identifycodeMapper.updateByPrimaryKeySelective(identifycode);
                            msg = ParameterAttribute.FORGET_PASS + identifycode.getCode() + ",有效期为10分钟";

                            SendmsgCount sendmsgCount = new SendmsgCount();
                            sendmsgCount.setId(UUID.randomUUID().toString());
                            sendmsgCount.setPhone(phone);
                            sendmsgCount.setTotalnum("1");
                            sendmsgCount.setCreatetime(new Date());
                            sendmsgCountMapper.insertSelective(sendmsgCount);
                        }
//                    }
                    } else {
                        Identifycode sidentifycode = new Identifycode();
                        sidentifycode.setId(UUID.randomUUID().toString());
                        sidentifycode.setPhone(phone);
                        //前期
                        // sidentifycode.setCode("666666");
                        sidentifycode.setCode(VcodeUtil.generateWord());
                        sidentifycode.setCreateTime(new Date());
                        identifycodeMapper.insertSelective(sidentifycode);
                        msg = ParameterAttribute.FORGET_PASS + sidentifycode.getCode() + ",有效期为10分钟";

                        //添加短信发送的次数
                        Map reqMap = new HashMap();
                        reqMap.put(ParameterAttribute.PHONE, phone);
                        reqMap.put(ParameterAttribute.CREATETIME, "%" + DataUtil.getYearDate() + "%");
                        List<SendmsgCount> sendmsgCountList = sendmsgCountMapper.selectByPage(reqMap);
                        if (sendmsgCountList.size() > 0) {//是否今天发过
                            SendmsgCount sendmsgCount = sendmsgCountList.get(0);
                            if (Integer.valueOf(sendmsgCount.getTotalnum()) >= Integer.valueOf(ParameterAttribute.MSG_COUNT)) {//今天发送是否超过5条
                                returnMap = RequestConstant.getRequestcodeDesc(143);
                                return returnMap;
                            } else {
                                sendmsgCount.setTotalnum(String.valueOf(Integer.valueOf(sendmsgCount.getTotalnum()) + 1));
                                sendmsgCount.setCreatetime(new Date());
                                sendmsgCountMapper.updateMsgCountByID(sendmsgCount);
                            }
                        } else {
                            SendmsgCount sendmsgCount = new SendmsgCount();
                            sendmsgCount.setId(UUID.randomUUID().toString());
                            sendmsgCount.setPhone(phone);
                            sendmsgCount.setTotalnum("1");
                            sendmsgCount.setCreatetime(new Date());
                            sendmsgCountMapper.insertSelective(sendmsgCount);
                        }

                    }
                }

//                int flag = SendMSG.sendMsg(msg, phone);
                 int flag = 1;
                if (flag > 0) {
                    //添加发短信的记录
                    Sendmsg sendmsg = new Sendmsg();
                    sendmsg.setId(UUID.randomUUID().toString());
                    sendmsg.setContent(msg);
                    sendmsg.setPhone(phone);
                    sendmsg.setType("0");//被发送的类型（0注册，1忘记密码，2录入孩子新用户）
                    sendmsg.setCreatetime(new Date());
                    sendmsgMapper.insertSelective(sendmsg);

                    returnMap = RequestConstant.getRequestcodeDesc(0);
                    returnMap.put(ParameterAttribute.DATA, null);
                    return returnMap;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    /**
     * 验证验证码
     *
     * @param phone
     * @param verifyCode
     * @return
     */
    @Override
    public Map checkVerifyCode(String phone, String verifyCode) {
        Map returnMap=new HashedMap();
        Identifycode identifycode = identifycodeMapper.selectByPhone(phone);
        //判断是否有验证码信息
        if (identifycode == null) {
            returnMap = RequestConstant.getRequestcodeDesc(152);
            return returnMap;
        }
//        Long datediff = DataUtil.timeDiffer(new Date(), identifycode.getCreateTime());
//        //判断验证码是否超时
//        if (datediff > 60 * 10) {
//            returnMap = RequestConstant.getRequestcodeDesc(103);
//            return returnMap;
//        }
        if (!identifycode.getCode().trim().equals(verifyCode)) {
            returnMap = RequestConstant.getRequestcodeDesc(151);
            return returnMap;
        }
        returnMap = RequestConstant.getRequestcodeDesc(0);
        return returnMap;
    }
}
