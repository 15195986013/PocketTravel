package com.njbd.pt.service.FeedBack.Impl;

import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.dao.AppFeebackMapper;
import com.njbd.pt.dao.UserInfoMapper;
import com.njbd.pt.model.AppFeeback;
import com.njbd.pt.model.UserInfo;
import com.njbd.pt.request.RequestConstant;
import com.njbd.pt.service.FeedBack.FeedBackService;
import com.njbd.pt.utils.map.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李建成
 * @date 2016/12/1-17:08.on NJBD
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private AppFeebackMapper appFeebackMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Map<String, Object> getAllFeedback(Map<String, Object> querymap) {
        Map returnMap = new HashMap();
        querymap.put(ParameterAttribute.DELFLAG, 0);
        try {
            List<AppFeeback> appFeebacks = appFeebackMapper.selectByPage(querymap);
            List<Map> Returnlist = new ArrayList<Map>();
            for (AppFeeback appFeeback : appFeebacks) {
                MapUtils mapUtils = new MapUtils();
                String UserName = "";
                if (StringUtils.isNotEmpty(appFeeback.getUserId())){
                    UserInfo userInfo= userInfoMapper.selectByPrimaryUserId(appFeeback.getUserId());
                    UserName=  userInfo.getNickname()== null|| "null".equals(userInfo.getNickname())?"":userInfo.getNickname();
                }
                Map paramMap = mapUtils.getReturnMapValues(
                        ParameterAttribute.FEED_BACK_ID, appFeeback.getId(),
                        ParameterAttribute.CLIENT_TYPE, appFeeback.getClientType(),
                        ParameterAttribute.CREATETIME, appFeeback.getCreateTime(),
                        ParameterAttribute.NICK_NAME, UserName,
                        ParameterAttribute.CONTENT, appFeeback.getContent()
                );
                Returnlist.add(paramMap);
            }
            returnMap = RequestConstant.getRequestcodeDesc(0);
            returnMap.put(ParameterAttribute.PAGE_ROWS, Returnlist);
            querymap.remove(ParameterAttribute.PAGE_START);
            querymap.remove(ParameterAttribute.PAGE_END);
            List<AppFeeback> list = appFeebackMapper.selectByPage(querymap);
            returnMap.put(ParameterAttribute.PAGE_TOTAl, list.size());
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }
}
