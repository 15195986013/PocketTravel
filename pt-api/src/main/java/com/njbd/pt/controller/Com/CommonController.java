package com.njbd.pt.controller.Com;

import com.njbd.pt.attribute.InterfaceAddressAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.model.AppFeeback;
import com.njbd.pt.service.Com.AppFeedBackService;
import com.njbd.pt.service.Com.VersionService;
import com.njbd.pt.utils.map.JsonUtil;
import com.njbd.pt.utils.string.UUIDUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by 李建成
 * on 2016/11/28.
 */
@Controller
@RequestMapping(value = InterfaceAddressAttribute.COMMON)
public class CommonController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private VersionService versionService;

    @Autowired
    private AppFeedBackService appFeedBackService;

    /**
     * 版本检查更新
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.CHECKUPDATE_ACTION, method = RequestMethod.POST)
    public Map CheckedUpdate(HttpServletResponse response) {

        Map returnMap = new HashedMap();
        try {
            response.setContentType("application/json;charset=UTF-8");
            returnMap = versionService.CheckVersion();
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《CommonController CheckedUpdate 》 error", e);
        }
        return returnMap;
    }

    /**
     * 意见反馈
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.FEEDBACK_ACTION, method = RequestMethod.POST)
    public Map addAppFeedBack(HttpServletResponse response,
                              @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params) {
        Map<String, Object> returnMap = new HashedMap();
        Map requestMap = JsonUtil.json2map(params);
        String userId = (String) requestMap.get(ParameterAttribute.USER_ID);
        String clientType = (String) requestMap.get(ParameterAttribute.CLIENT_TYPE);
        String content = (String) requestMap.get(ParameterAttribute.CONTENT);
        try {
            response.setContentType("application/json;charset=UTF-8");
            AppFeeback appFeeback = new AppFeeback();
            appFeeback.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
            appFeeback.setUserId(userId);
            appFeeback.setClientType(clientType);
            appFeeback.setContent(content);
            appFeeback.setCreateTime(new Date());
            returnMap = appFeedBackService.addAppFeedBack(appFeeback);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《CommonController addAppFeedBack》 error", e);
        }
        return returnMap;
    }


}
