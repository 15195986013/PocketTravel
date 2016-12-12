package com.njbd.pt.utils.map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李建成
 * ON:  2016/10/12 13:27
 * *****  private 泛型 count factory ********
 */
public class MapUtils<INPUT> {
    /**
     * 公共Map的返回参数
     * ********** 传入参数（input_param） 伸缩定长 *********
     * ********** 接收参数 （out_param）  伸缩定长 **********
     *
     * @param
     * @return
     */


    private final static Logger logger = LoggerFactory.getLogger(MapUtils.class);

    private INPUT key_param;//key
    private INPUT val_param;//value

    public INPUT getKey_param() {
        return key_param;
    }

    public void setKey_param(INPUT key_param) {
        this.key_param = key_param;
    }

    public INPUT getVal_param() {
        return val_param;
    }

    public void setVal_param(INPUT val_param) {
        this.val_param = val_param;
    }

    public Map getReturnMapValues(INPUT... inputs_params) {
        HashMap returnMap = new HashMap();//返回Map 集合
        try {
            for (int i = 0; i < inputs_params.length; i++) {
                if (inputs_params != null) {
                    if (i % 2 == 0) {
                        this.setKey_param(inputs_params[i]);
                    } else {
                        this.setVal_param(inputs_params[i]);
                    }
                    returnMap.put(this.getKey_param() == null ? "" : this.getKey_param(), this.getVal_param() == null ? "" : this.getVal_param());
                }
            }
        } catch (Exception e) {
            logger.debug("pm  MapUtils<INPUT> 《getReturnMapValues》 cause by:", e);
            e.printStackTrace();
        }
        return returnMap;
    }


    /**
     * getRequestMapMsg 返回请求信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static Map getRequestMapMsg(Integer code, String msg) {
        Map returnMap = new HashMap();
        try {
            returnMap.put("code", code);
            returnMap.put("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pm  MapUtils<INPUT> 《getRequestMapMsg》 cause by:", e);
        }
        return returnMap;
    }

    public static <T extends Object> Map PutLikeMaps(Map querymap, T searchName, T searchType, T... LikeTypes) {
        for (T LikeType : LikeTypes) {
            if (searchType.equals(LikeType)) {
                querymap.put(String.valueOf(LikeType).trim(), "%" + searchName + "%");
            } else {
                querymap.put(String.valueOf(LikeType).trim(), null);
            }

        }
        return querymap;
    }

    public static Map<String, Object> BeanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("pm  MapUtils<INPUT> 《beanToMap》 cause by:", e);
        }
        return params;
    }

    public static Object MapToBean(Map map, Class type) throws IntrospectionException, IllegalAccessException, InstantiationException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        Object obj = type.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                try {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(obj, args);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logger.debug("pm  MapUtils<INPUT> 《MapToBean》 cause by:", e);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    logger.debug("pm  MapUtils<INPUT> 《MapToBean》 cause by:", e);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    logger.debug("pm  MapUtils<INPUT> 《MapToBean》 cause by:", e);
                }
            }
        }
        return obj;
    }

    /**
     * 数据刷新
     */
    public static void MapRemovePageSize(Map returnMap, Map queryMap, List ReturnList) {
        returnMap.put("rows", ReturnList);
        queryMap.remove("start");
        queryMap.remove("end");
        return;
    }


}