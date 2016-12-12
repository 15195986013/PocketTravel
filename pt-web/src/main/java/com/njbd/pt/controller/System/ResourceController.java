package com.njbd.pt.controller.System;


import com.njbd.pt.attribute.HttpAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.attribute.ViewPrefixAttribute;
import com.njbd.pt.service.System.ResourceService;
import com.njbd.pt.utils.map.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李建成
 * ON:  2016/10/21 10:16
 */
@Controller
@RequestMapping(value = HttpAttribute.ADMIN_HTML)
public class ResourceController {


    @Autowired
    private ResourceService resourceService;


    @RequestMapping(value = HttpAttribute.RESOURCE_MANAGER_HTML)
    private ModelAndView ResourceManagerInIt() {
        return new ModelAndView(ViewPrefixAttribute.RESOURCE_MANAGER_PREFIX);
    }


    @ResponseBody
    @RequestMapping(value =HttpAttribute.GETALLRESOURCE_JSON)
    public Map<String, Object> getAllResource(
            @RequestParam(value = ParameterAttribute.SEARCH_NAME, required = false, defaultValue = "") String searchName,
            @RequestParam(value = ParameterAttribute.SEARCH_TYPE, required = false, defaultValue = "") String searchType,
            @RequestParam(value = ParameterAttribute.PAGE, required = false, defaultValue = "1") Integer page,
            @RequestParam(value = ParameterAttribute.ROWS, required = false, defaultValue = "10") Integer rows) throws Exception {
        Map<String, Object> querymap = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(searchName)) {
            querymap = MapUtils.PutLikeMaps(querymap, searchName, searchType,
                    ParameterAttribute.NAME,
                    ParameterAttribute.URL
            );
        }

        querymap.put(ParameterAttribute.PAGE_START, (page - 1) * rows);
        querymap.put(ParameterAttribute.PAGE_END, rows);
        Map<String, Object> returnMap = resourceService.getAllResource(querymap);
        return returnMap;
    }


    @ResponseBody
    @RequestMapping(value = "getSelectAllResourcer.json", method = RequestMethod.POST)
    public Map getSelectAllResources(
    ) throws Exception {
        Map queryMap = new HashMap();
        Map<String, Object> returnMap = resourceService.getSelectAllResources(queryMap);
        return returnMap;
    }


    @ResponseBody
    @RequestMapping(value = "addResource.json")
    public Map<String, Object> addResource(
            @RequestParam(value = ParameterAttribute.RESOURCE_ID, required = true, defaultValue = "") String resourceId,
            @RequestParam(value = ParameterAttribute.NAME, required = false, defaultValue = "") String name,
            @RequestParam(value = ParameterAttribute.PARENT_ID, required = false, defaultValue = "") String parentId,
            @RequestParam(value = ParameterAttribute.URL, required = false, defaultValue = "") String url
    ) throws Exception {
        MapUtils mapUtils = new MapUtils();
        Map<String, Object> querymap = mapUtils.getReturnMapValues(
                ParameterAttribute.RESOURCE_ID, resourceId,
                ParameterAttribute.URL, url,
                ParameterAttribute.PARENT_ID, parentId,
                ParameterAttribute.NAME, name
        );
        Map<String, Object> returnMap;
        if (resourceId.equals("-1")) {
            returnMap = resourceService.addResource(querymap);
            return returnMap;
        } else {
            returnMap = resourceService.updateResource(querymap);
            return returnMap;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/selectResourceById.json")
    public Map<String, Object> selectResourceById(
            @RequestParam(value = ParameterAttribute.RESOURCE_ID, required = false, defaultValue = "") String resourceId) throws Exception {
        Map<String, Object> returnMap = resourceService.selectResourceById(resourceId);
        return returnMap;
    }


}
