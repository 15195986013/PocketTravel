package com.njbd.pt.service.Version.Impl;


import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.dao.VersionMapper;
import com.njbd.pt.model.Version;
import com.njbd.pt.request.RequestConstant;
import com.njbd.pt.service.Version.VersionService;
import com.njbd.pt.utils.map.MapUtils;
import com.njbd.pt.utils.string.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 李建成
 * ON:  2016/10/19 17:00
 */
@Service
public class VersionServiceImpl implements VersionService {

    private final static Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);
    @Autowired
    private VersionMapper versionMapper;

    @Override
    public Map<String, Object> getAllVersion(Map<String, Object> querymap) {
        Map returnMap;
        try {
            List<Version> versionList = versionMapper.selectByPage(querymap);
            List<Object> returnversions = new ArrayList<Object>();//存放返回来电集合
            for (Version version : versionList) {
                MapUtils mapUtils = new MapUtils();
                Map reVersionMap = mapUtils.getReturnMapValues(
                        ParameterAttribute.VERSION_ID, version.getId(),
                        ParameterAttribute.VERSION_URL, version.getUrl(),
                        ParameterAttribute.VERSION_NAME, version.getVersionName(),
                        ParameterAttribute.VERSION_CONTENT, version.getContent(),
                        ParameterAttribute.CLIENT_TYPE, version.getClientType(),
                        ParameterAttribute.MAX_VERION, version.getVersion(),
                        ParameterAttribute.MIN_VRESION, version.getMinimumVersion(),
                        ParameterAttribute.CREATETIME, version.getCreateTime()
                );
                returnversions.add(reVersionMap);
            }
            returnMap = RequestConstant.getRequestcodeDesc(0);
            returnMap.put(ParameterAttribute.PAGE_ROWS, returnversions);
            querymap.remove(ParameterAttribute.PAGE_START);
            querymap.remove(ParameterAttribute.PAGE_END);
            returnMap.put(ParameterAttribute.PAGE_TOTAl, versionMapper.selectByPage(querymap).size());
            return returnMap;
        } catch (Exception e) {
            returnMap = RequestConstant.getRequestcodeDesc(100);
            e.printStackTrace();
            logger.debug("pm 《getAllVersion》 error cause by :", e);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> addVersion(Map<String, Object> querymap) {
        Map returnMap;
        try {
            Float minimumVersion = (Float) querymap.get(ParameterAttribute.MIN_VRESION);
            Float maxversion = (Float) querymap.get(ParameterAttribute.MAX_VERION);
            Integer clientType = Integer.valueOf(String.valueOf(querymap.get(ParameterAttribute.CLIENT_TYPE)));
            String versionName = String.valueOf(querymap.get(ParameterAttribute.VERSION_NAME));
            String url = String.valueOf(querymap.get(ParameterAttribute.VERSION_URL));
            String share_url = String.valueOf(querymap.get(ParameterAttribute.VERSION_SHARE_URL));
            String content = (String) querymap.get(ParameterAttribute.VERSION_CONTENT);
            Version version = new Version();
            version.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
            version.setClientType(clientType);
            version.setContent(content);
            version.setShareUrl(share_url);
            version.setVersionName(versionName);
            version.setUrl(url);
            version.setVersion(maxversion);
            version.setMinimumVersion(minimumVersion);
            version.setCreateTime(new Date());
            versionMapper.insertSelective(version);
            returnMap = RequestConstant.getRequestcodeDesc(0);
            return returnMap;
        } catch (Exception e) {
            logger.debug("pm 《 addVersion 》 error cause by:", e);
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> updateVersion(Map<String, Object> querymap) {
        Map returnMap;
        try {
            Version version = versionMapper.selectByPrimaryKey((String) querymap.get(ParameterAttribute.VERSION_ID));
            Float minimumVersion = (Float) querymap.get(ParameterAttribute.MIN_VRESION);
            Float maxversion = (Float) querymap.get(ParameterAttribute.MAX_VERION);
            Integer clientType = Integer.valueOf(String.valueOf(querymap.get(ParameterAttribute.CLIENT_TYPE)));
            String versionName = String.valueOf(querymap.get(ParameterAttribute.VERSION_NAME));
            String url = String.valueOf(querymap.get(ParameterAttribute.VERSION_URL));
            String share_url = String.valueOf(querymap.get(ParameterAttribute.VERSION_SHARE_URL));
            String content = (String) querymap.get(ParameterAttribute.VERSION_CONTENT);
            version.setClientType(clientType);
            version.setContent(content);
            version.setVersionName(versionName);
            version.setUrl(url);
            version.setVersion(maxversion);
            version.setShareUrl(share_url);
            version.setMinimumVersion(minimumVersion);
            version.setCreateTime(new Date());
            versionMapper.updateByPrimaryKeySelective(version);
            returnMap = RequestConstant.getRequestcodeDesc(0);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pm 《 updateVersion 》 error cause by:", e);
            return RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> selectVersionById(String versionId) {
        Map returnMap;
        try {
            Version version = versionMapper.selectByPrimaryKey(versionId);
            MapUtils mapUtils = new MapUtils();
            returnMap = mapUtils.getReturnMapValues(
                    ParameterAttribute.VERSION_ID, version.getId(),
                    ParameterAttribute.VERSION_URL, version.getUrl(),
                    ParameterAttribute.VERSION_NAME, version.getVersionName(),
                    ParameterAttribute.VERSION_CONTENT, version.getContent(),
                    ParameterAttribute.CLIENT_TYPE, version.getClientType(),
                    ParameterAttribute.MAX_VERION, version.getVersion(),
                    ParameterAttribute.MIN_VRESION, version.getMinimumVersion(),
                    ParameterAttribute.CREATETIME, version.getCreateTime()
            );
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("pm《updateVersion》 error cause by:", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> deleteVersion(Map<String, Object> querymap) {
        Map returnMap = new HashMap();
        try {
            String versionId = String.valueOf(querymap.get(ParameterAttribute.VERSION_ID));
            versionMapper.deleteByPrimaryKey(versionId);
            returnMap.put(ParameterAttribute.CODE, 0);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("pm《deleteVersion》 error cause by:", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }
}
