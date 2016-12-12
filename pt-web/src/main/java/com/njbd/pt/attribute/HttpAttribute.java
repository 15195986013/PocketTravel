package com.njbd.pt.attribute;

/**
 * Created by 李建成
 * on 2016/11/28.
 * 实现页面跳转常量
 */
public class HttpAttribute {
    //前缀地址
    public static final String ADMIN_HTML = "admin";
    //登录地址
    public static final String LOGIN_HTML = "/login.html";
    //登录拦截地址
    public static final String LOGINCHECKD_JSON = "logincheckd.json";
    //首页地址
    public static final String INDEX_HTML = "index.html";
    //系统信息地址
    public static final String INDEX_SYSTEMINFO_JSON = "indexSystemInfo.json";
    //更新系统地址
    public static final String SAVE_SYSTEMINFO_JSON = "saveSystemInfo.json";
    //系统登出地址
    public static final String LOGIN_OUT_HTML = "logout.html";
    //权限资源分配地址
    public static final String MENU_JSON = "/menu.json";
    //权限管理地址
    public static final String RESOURCE_MANAGER_HTML = "resourcemanager.html";
    //获取权限地址
    public static final String GETALLRESOURCE_JSON = "getAllResource.json";
    //系统信息地址
    public static final String SYSTEMMANAGER_HTML = "systemmanager.html";
    // 管理员管理权限请求地址
    public static final String USER_MANAGER_HTML = "usermanager.html";
    //获取权限列表地址
    public static final String GETALLUSER_JSON = "getAllUser.json";


    //获取权限列表地址
    public static final String GETALLDRIVER_JSON = "getAllDriver.json";

    //逻辑删除管理员
    public static final String DELETE_ADMIN_JSON = "/deleteAdmin.json";
    //逻辑启用状态
    public static final String CHANGE_ROLE_ABLE_JSON = "/changeEnabled.json";
    //逻辑添加管理员
    public static final String ADD_ADMIN_JSON = "/addAdmin.json";
    //获取管理员信息
    public static final String SELECT_BY_ADMINID_JSON = "/selectByAdminId.json";

    public static final String GET_ADMIN_ROLES_JSON = "/getAdminRoles.json";

    public static final String SAVE_ADMIN_ROLE_JSON = "/saveAdminRole.json";


    /**
     * 角色
     */

    public static final String ROLE_MANAGER_HTML= "/rolemanager.html";

    public static final String ADD_ROLE_JSON= "/addRole.json";

    public static final String GET_ALLROLE_JSON = "/getAllRole.json";

    public static final String SELECT_ROLEBYID_JSON = "/selectRoleById.json";

    public static final String CHANG_EROLE_ENABLED_JSON =  "/changeRoleEnabled.json";

    public static final String DELETE_ROLE_JSON = "/deleteRole.json";

    public static final String GET_MANAGER_TBFUNCTION_JSON ="/getmanagertbfunction.json";

    public static final String SAVE_MANAGER_FUNCTION_JSON = "/savemanagerfunction.json";

    /**
     * todo 意见反馈
     */
    public static final String FEED_BACK_MANAGER_HTML = "/feedbackmanager.html";



    public static final String GET_APP_FEEDBACK_JSON =  "getAppFeedBack.json";


    /**
     * todo 行程详情
     */


    public static final String POST_MANAGER_HTML = "/postmanager.html";
    public static final String GET_POSTLIST_JSON = "getPostList.json";


    //司机审核地址
    public static final String DRIVER_HTML = "drivermanager.html";
    public static final String CANGE_STATUS_JSON= "changeStatus.json";






}

