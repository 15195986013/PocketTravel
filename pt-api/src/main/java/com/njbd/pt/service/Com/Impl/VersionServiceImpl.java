package com.njbd.pt.service.Com.Impl;

import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.dao.VersionMapper;
import com.njbd.pt.model.Version;
import com.njbd.pt.service.Com.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.njbd.pt.attribute.RequestConstant.getRequestcodeDesc;

/**
 * Created by 李建成
 *
 * @date 2016/12/2-10:13.on NJBD
 */
@Service
public class VersionServiceImpl implements VersionService {

    private static final Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);

    @Autowired
    private VersionMapper versionMapper;


    public Map CheckVersion() {
        Map returnMap;
        logger.info("====版本检查开始====");
        try {
            returnMap = getRequestcodeDesc(0);
            List<Version> versions = versionMapper.selectByPage(null);
            if (versions.size() > 0) {
                Map paramMap = new HashMap();
                Version version = versions.get(1);
                paramMap.put(ParameterAttribute.CLIENT_TYPE, version.getClientType());
                paramMap.put(ParameterAttribute.DOWN_URL, version.getUrl());
                paramMap.put(ParameterAttribute.SHARE_URL, version.getUrl());
                paramMap.put(ParameterAttribute.MAX_VERSION, version.getVersion());
                paramMap.put(ParameterAttribute.MIN_VERSION, version.getMinimumVersion());
                paramMap.put(ParameterAttribute.DESCRIPTION, version.getContent());
                returnMap.put(ParameterAttribute.DATA, paramMap);
                return returnMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = getRequestcodeDesc(1001);
            return returnMap;
        }
        return returnMap;
    }
}
