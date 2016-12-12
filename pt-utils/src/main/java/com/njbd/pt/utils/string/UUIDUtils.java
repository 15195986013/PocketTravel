package com.njbd.pt.utils.string;

import java.util.UUID;

/**
 * Created by 李建成
 * ON:  2016/9/20 16:24
 */
public class UUIDUtils {
    /**
     * 获取UUID
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
    /**
     * 去除 -
     */
    public static String getSimpleUUID( String s) {
        return s.replace("-","").toString();
    }
}
