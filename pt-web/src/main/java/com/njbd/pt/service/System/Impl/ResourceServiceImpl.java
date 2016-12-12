package com.njbd.pt.service.System.Impl;


import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.dao.ResourceMapper;
import com.njbd.pt.model.Resource;
import com.njbd.pt.request.RequestConstant;
import com.njbd.pt.service.System.ResourceService;
import com.njbd.pt.utils.map.MapUtils;
import com.njbd.pt.utils.string.UUIDUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 李建成
 * ON:  2016/10/21 10:27
 */

@Service
public class ResourceServiceImpl implements ResourceService {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public Map<String, Object> getAllResource(Map<String, Object> querymap) {
        Map returnMap;
        try {
            List<Resource> resourceList = resourceMapper.selectByPage(querymap);
            List<Object> resources = new ArrayList<Object>();
            for (Resource resource : resourceList) {
                MapUtils mapUtils = new MapUtils();
                Map paramMap = mapUtils.getReturnMapValues(
                        ParameterAttribute.RESOURCE_ID, resource.getId(),
                        ParameterAttribute.NAME, resource.getName(),
                        ParameterAttribute.URL, resource.getUrl(),
                        ParameterAttribute.PARENT_ID, resource.getParentId(),
                        ParameterAttribute.CREATETIME, resource.getCreateTime()
                );
                if (resource.getParentId().equals("0")) {
                    paramMap.put(ParameterAttribute.LEVEL, "一级菜单");
                } else {
                    String resourcesParent = resourceMapper.selectByPrimaryKey(resource.getParentId()).getName();
                    paramMap.put(ParameterAttribute.LEVEL, resourcesParent);
                }
                resources.add(paramMap);
            }
            returnMap = RequestConstant.getRequestcodeDesc(0);
            returnMap.put(ParameterAttribute.PAGE_ROWS, resources);
            querymap.remove(ParameterAttribute.PAGE_START);
            querymap.remove(ParameterAttribute.PAGE_END);
            returnMap.put(ParameterAttribute.PAGE_TOTAl, resourceMapper.selectByPage(querymap).size());
            return returnMap;
        } catch (Exception e) {
            returnMap = RequestConstant.getRequestcodeDesc(100);
            e.printStackTrace();
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getSelectAllResources(Map<String, Object> querymap) {
        Map returnMap = new HashMap();
        try {
            querymap.put(ParameterAttribute.PARENT_ID, "0");
            List<Resource> resourceList = resourceMapper.selectByPage(querymap);
            List<Object> resources = new ArrayList<Object>();
            for (Resource resource : resourceList) {
                MapUtils mapUtils = new MapUtils();
                Map paramMap = mapUtils.getReturnMapValues(
                        ParameterAttribute.RESOURCE_ID, resource.getId(),
                        ParameterAttribute.NAME, resource.getName()
                );
                resources.add(paramMap);
            }
            returnMap.put(ParameterAttribute.CODE, 0);
            returnMap.put(ParameterAttribute.MSG, "获取公司列表成功");
            returnMap.put(ParameterAttribute.DATA, resources);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
            logger.debug("pm 《getSelectAllResources》 error cause by:", e);
        }
        return returnMap;
    }


    @Override
    public Map<String, Object> updateResource(Map<String, Object> querymap) {


        Map returnMap;
        try {
            Resource resource = resourceMapper.selectByPrimaryKey(String.valueOf(querymap.get(ParameterAttribute.RESOURCE_ID)));
            resource.setParentId(String.valueOf(querymap.get(ParameterAttribute.PARENT_ID)));
            resource.setName(String.valueOf(querymap.get(ParameterAttribute.NAME)));
            resource.setUrl(String.valueOf(querymap.get(ParameterAttribute.URL)));
            resourceMapper.updateByPrimaryKeySelective(resource);
            returnMap = RequestConstant.getRequestcodeDesc(0);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
            logger.debug("pm 《addResource》 error cause by:", e);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> addResource(Map<String, Object> querymap) {
        Map returnMap;
        try {
            Resource resource = new Resource();
            resource.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
            resource.setParentId(String.valueOf(querymap.get(ParameterAttribute.PARENT_ID)));
            resource.setName(String.valueOf(querymap.get(ParameterAttribute.NAME)));
            resource.setUrl(String.valueOf(querymap.get(ParameterAttribute.URL)));
            resource.setCreateTime(new Date());
            returnMap = RequestConstant.getRequestcodeDesc(0);
            resourceMapper.insertSelective(resource);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
            logger.debug("pm 《addResource》 error cause by:", e);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> selectResourceById(String resourceId) {
        Map returnMap;
        try {
            Resource resource = resourceMapper.selectByPrimaryKey(resourceId);
            MapUtils mapUtils = new MapUtils();
            returnMap = mapUtils.getReturnMapValues(
                    ParameterAttribute.RESOURCE_ID, resource.getId(),
                    ParameterAttribute.NAME, resource.getName(),
                    ParameterAttribute.URL, resource.getUrl(),
                    ParameterAttribute.PARENT_ID, resource.getParentId(),
                    ParameterAttribute.CREATETIME, resource.getCreateTime()
            );
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
            logger.error("pm《selectResourceById》 error cause by:", e);
        }
        return returnMap;
    }


}
