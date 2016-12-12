package com.njbd.pt.attribute;

/**
 * Created by 李建成
 * on 2016/11/28.
 * todo 视图解析器常量便于后期维护
 * todo 视图解析器 前缀 ${Contentext}/WEB-INF/views
 */
public class ViewPrefixAttribute {
    // TODO: 2016/11/28  管理员登录视图前缀
    public static final String LOGIN_PREFIX = "/manager/system/login";
    // TODO: 2016/11/28 主页视图前缀
    public static final String INDEX_PREFIX = "/manager/system/index";
    // TODO: 2016/11/28  资源视图前缀
    public static final String RESOURCE_MANAGER_PREFIX = "/manager/system/resourcemanager";
    // TODO: 2016/11/28  系统信息视图前缀
    public static final String SYSTEM_MANAGER_PREFIX = "/manager/system/systemmanager";
    // TODO: 2016/11/28  用户管理视图前缀
    public static final String USER_MANAGER_PREFIX = "/manager/user/usermanager";
    // TODO: 2016/11/28 角色视图前缀
    public static final String ROLE_MANAGER_PREFIX = "/manager/user/rolemanager";
    // TODO: 2016/11/28 意见反馈视图前缀
    public static final String FEED_BACK_MANAGER_PRIFX = "/manager/feedback/feedbackmanager";
    // TODO: 2016/11/28 行程视图前缀
    public static final String POST_MANAGER_PRIFX = "/manager/post/postmanager";
    public static final String DRIVER_MANAGER_PRIFX = "/manager/user/drivermanager";

}
