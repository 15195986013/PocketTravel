package com.njbd.pt.controller.Com;


import com.njbd.pt.attribute.InterfaceAddressAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.service.Com.PostService;
import com.njbd.pt.utils.date.DataUtil;
import com.njbd.pt.utils.map.JsonUtil;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = InterfaceAddressAttribute.TAXI)
public class TaxiController {
    private static final Logger logger = LoggerFactory.getLogger(TaxiController.class);

    @Autowired
    private PostService postService;

    /**
     * 发布网约车
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.PUBLISH_ACTION, method = RequestMethod.POST)
    public Map addPost(HttpServletResponse response,
                       @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params) {
        Map requestMap = JsonUtil.json2map(params);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String start = (String) requestMap.get(ParameterAttribute.START);
        Double sLon = Double.parseDouble(String.valueOf(requestMap.get(ParameterAttribute.SLON)));
        Double sLat = Double.parseDouble(String.valueOf(requestMap.get(ParameterAttribute.SLAT)));
        Double eLon = Double.parseDouble(String.valueOf(requestMap.get(ParameterAttribute.ELON)));
        Double eLat = Double.parseDouble(String.valueOf(requestMap.get(ParameterAttribute.ELAT)));
        String end = (String) requestMap.get(ParameterAttribute.END);

        String time = (String) requestMap.get(ParameterAttribute.TIME);
        String userId = (String) requestMap.get(ParameterAttribute.USER_ID);
        String valuation = (String) requestMap.get(ParameterAttribute.VALUATION);
        try {
            response.setContentType("application/json;charset=UTF-8");
            returnMap = postService.addPost(userId, start, end, sLon, sLat, eLon, eLat, time, valuation);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《PersonalController addPost》 error", e);
        }
        return returnMap;
    }

    /**
     * 取消网约车发布
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.CANCELPUBLISH_ACTION, method = RequestMethod.POST)
    public Map deletePost(HttpServletResponse response,
                          @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {

            Map requestMap = JsonUtil.json2map(params);
            String tripId = (String) requestMap.get(ParameterAttribute.TRIPID);
            response.setContentType("application/json;charset=UTF-8");
            returnMap = postService.deletePost(tripId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《PersonalController deletePost》 error", e);
        }
        return returnMap;
    }

    /**
     * 抢单
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.RECEIVE_ACTION, method = RequestMethod.POST)
    public Map receivePost(HttpServletResponse response,
                           @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Map requestMap = JsonUtil.json2map(params);
            String tripId = (String) requestMap.get(ParameterAttribute.TRIPID);
            response.setContentType("application/json;charset=UTF-8");
            returnMap = postService.receivePost(tripId);

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《PersonalController receivePost》 error", e);
        }
        return returnMap;
    }

    /**
     * 获取行程列表
     */
    @ResponseBody
    @RequestMapping(value = InterfaceAddressAttribute.TRIPLIST_ACTION, method = RequestMethod.POST)
    public Map<String, Object> getTripList(HttpServletResponse response,
                                           @RequestParam(value = ParameterAttribute.PARAMS, required = false, defaultValue = "") String params
    ) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            response.setContentType("application/json;charset=UTF-8");
            Map requestMap = JsonUtil.json2map(params);
            Integer startIndex = Integer.valueOf( String.valueOf(requestMap.get(ParameterAttribute.STARTINDEX)));
            Long time = Long.valueOf(String.valueOf( requestMap.get(ParameterAttribute.TIME).toString()));
            Integer pageSize = Integer.parseInt( String.valueOf( requestMap.get(ParameterAttribute.PAGESIZE)));
            String userId = (String) requestMap.get(ParameterAttribute.USER_ID);
            Map<String, Object> querymap = new HashMap<String, Object>();
            querymap.put(ParameterAttribute.PAGE_START, (startIndex - 1) * pageSize);
            querymap.put(ParameterAttribute.PAGE_END, startIndex * pageSize);
            querymap.put(ParameterAttribute.USER_ID, userId);
            String date = DataUtil.formatMillisTo(new Date(time), "yyyy-MM-dd HH:mm:ss");
            querymap.put(ParameterAttribute.TIME, "%" + date + "%");
            returnMap = postService.getTripList(querymap);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pt 《PersonalController getTripList》 error", e);
        }
        return returnMap;
    }


}
