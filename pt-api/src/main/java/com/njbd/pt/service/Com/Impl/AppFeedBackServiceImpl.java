package com.njbd.pt.service.Com.Impl;

import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.attribute.RequestConstant;
import com.njbd.pt.dao.AppFeebackMapper;
import com.njbd.pt.model.AppFeeback;
import com.njbd.pt.service.Com.AppFeedBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class AppFeedBackServiceImpl implements AppFeedBackService {
    private static final Logger logger = LoggerFactory.getLogger(AppFeedBackServiceImpl.class);
    @Autowired
    private AppFeebackMapper appFeebackMapper;

    public Map addAppFeedBack(AppFeeback appFeeback) {
        Map returnMap;
        logger.info("意见反馈开始：");
        try {
            System.out.println(appFeeback);
            int n = appFeebackMapper.insertSelective(appFeeback);
            if (n == 1) {
                returnMap = RequestConstant.getRequestcodeDesc(0);
                returnMap.put(ParameterAttribute.DATA, null);
            } else {
                returnMap = RequestConstant.getRequestcodeDesc(101);
                returnMap.put(ParameterAttribute.DATA, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA, null);
            return returnMap;
        }
        return returnMap;
    }

}
