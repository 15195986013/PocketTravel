package com.njbd.pt.utils.map;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李建成
 * on 2016/12/5.
 */
public class JsonUtil {
    public static Map<String, Object> json2map(String str) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap();
        try {
            map =  mapper.readValue(str, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static String map2json(Map<String, Object> map) {
        ObjectMapper mapper = new ObjectMapper();
        String returnStr = "";
        try {
            returnStr = mapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnStr;
    }
}
