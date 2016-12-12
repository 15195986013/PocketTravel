package com.njbd.pt.service.System;

import java.util.Map;

/**
 * Created by 李建成
 * ON:  2016/10/21 10:27
 */
public interface ResourceService {


    Map<String,Object> getAllResource(Map<String, Object> querymap);

    Map<String,Object> getSelectAllResources(Map<String, Object> querymap);

    Map<String,Object> updateResource(Map<String, Object> querymap);

    Map<String,Object> addResource(Map<String, Object> querymap);

    Map<String,Object> selectResourceById(String resourceId);
}
