package com.njbd.pt.utils;

import com.njbd.pt.utils.map.MapUtils;
import com.njbd.pt.utils.map.User;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by 李建成
 *
 * @date 2016/12/2-16:50.on NJBD
 */

public class MapTest {

    @Test
    public void MapUtiles() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        Map BeanMap = new HashedMap();
        BeanMap.put("job", "小花");
        BeanMap.put("age", "202");
        BeanMap.put("name", "驱蚊器");
        User user1 = (User) MapUtils.MapToBean(BeanMap, User.class);
        System.out.println("MapToBean: =>" + user1);
        User user = new User("李建成", "web", "20");
        Map SoutMap = MapUtils.BeanToMap(user);
        System.out.println("BeanToMap: =>" + SoutMap);

    }


}
