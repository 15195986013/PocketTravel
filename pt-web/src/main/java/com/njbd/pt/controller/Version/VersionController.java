package com.njbd.pt.controller.Version;

 
import com.njbd.pt.attribute.HttpAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.service.Version.VersionService;
import com.njbd.pt.utils.map.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
 

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李建成
 * ON:  2016/10/19 16:59
 */

@Controller
@RequestMapping(value = HttpAttribute.ADMIN_HTML)
public class VersionController {
    private final static Logger logger = LoggerFactory.getLogger(VersionController.class);


    @Autowired
    private VersionService versionService;

    @RequestMapping(value = "versionmanager.html")
    public ModelAndView VersionManager(HttpSession session) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        return new ModelAndView("manager/version/versionmanager", returnMap);
    }


    @RequestMapping(value = "getAllVersion.json")
    @ResponseBody
    public Map<String, Object> getAllVersion(

            @RequestParam(value = ParameterAttribute.SEARCH_NAME, required = false, defaultValue = "") String searchName,
            @RequestParam(value = ParameterAttribute.SEARCH_TYPE, required = false, defaultValue = "") String searchType,
            @RequestParam(value = ParameterAttribute.PAGE, required = false, defaultValue = "1") Integer page,
            @RequestParam(value = ParameterAttribute.ROWS, required = false, defaultValue = "10") Integer rows) throws Exception {
        Map<String, Object> querymap = new HashMap<String, Object>();


        if (!StringUtils.isEmpty(searchName)){
            querymap=  MapUtils.PutLikeMaps(querymap, searchName, searchType,
                    ParameterAttribute.VERSION_NAME,
                    ParameterAttribute.VERSION_CONTENT
            );
        }

        querymap.put(ParameterAttribute.PAGE_START, (page - 1) * rows);
        querymap.put(ParameterAttribute.PAGE_END, rows);
        Map<String, Object> returnMap = versionService.getAllVersion(querymap);
        return returnMap;
    }


    @ResponseBody
    @RequestMapping(value = "addVersion.json")
    public Map<String, Object> addVersion(
            @RequestParam(value = ParameterAttribute.VERSION_ID, defaultValue = "") String vesionId,
            @RequestParam(value = ParameterAttribute.MIN_VRESION, required = false, defaultValue = "") Float minimumVersion ,
            @RequestParam(value = ParameterAttribute.MAX_VERION, required = false, defaultValue = "") Float version,
            @RequestParam(value = ParameterAttribute.VERSION_CONTENT, required = false, defaultValue = "") String content,
            @RequestParam(value = ParameterAttribute.CLIENT_TYPE, required = false, defaultValue = "") Integer clientType,
            @RequestParam(value = ParameterAttribute.VERSION_NAME, required = false, defaultValue = "") String versionName,
            @RequestParam(value = ParameterAttribute.VERSION_URL, required = false, defaultValue = "") String url,
            @RequestParam(value = ParameterAttribute.VERSION_SHARE_URL, required = false, defaultValue = "") String shar_url
 ) throws Exception {
        MapUtils mapUtils = new MapUtils();
        Map<String, Object> querymap =mapUtils.getReturnMapValues(
                ParameterAttribute.VERSION_ID,vesionId,
                ParameterAttribute.MIN_VRESION,minimumVersion,
                ParameterAttribute.VERSION_URL,url,
                ParameterAttribute.VERSION_NAME,versionName,
                ParameterAttribute.MAX_VERION,version,
                ParameterAttribute.CLIENT_TYPE,clientType,
                ParameterAttribute.VERSION_CONTENT,content,
                ParameterAttribute.VERSION_SHARE_URL,shar_url
        );
        Map<String, Object> returnMap;
        if (vesionId.equals("-1")) {
            returnMap = versionService.addVersion(querymap);
            return returnMap;
        } else {
            returnMap = versionService.updateVersion(querymap);
            return returnMap;
        }
    }



    @ResponseBody
    @RequestMapping(value = "/selectVersionById.json")
    public Map<String, Object> selectVersionById(
            @RequestParam(value = ParameterAttribute.VERSION_ID, required = false, defaultValue = "") String versionId) throws Exception {
        Map<String, Object> returnMap = versionService.selectVersionById(versionId);
        return returnMap;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteVersion.json")
    public Map<String, Object> deleteVersion(
            @RequestParam(value = "ids", defaultValue = "") String ids) throws Exception {
        Map<String, Object> returnMap = null;
        Map<String, Object> querymap = new HashMap<String, Object>();
        String[] arr = ids.split(",");
        for (int i = 0; i < arr.length; i++){
            querymap.put(ParameterAttribute.VERSION_ID, arr[i]);
            returnMap = versionService.deleteVersion(querymap);
        }
        return returnMap;
    }
}
