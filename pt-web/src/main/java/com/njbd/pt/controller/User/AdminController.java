package com.njbd.pt.controller.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.njbd.pt.attribute.HttpAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.attribute.ViewPrefixAttribute;
import com.njbd.pt.request.RequestConstant;
import com.njbd.pt.service.User.AdminService;
import com.njbd.pt.utils.map.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李建成
 * on 2016/11/29.
 */
@Controller
@RequestMapping(value = HttpAttribute.ADMIN_HTML)
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * @param session
     * @param adminAccount
     * @param adminPassword
     * @return
     * @throws Exception
     * @info admin LoginChecked
     */
    @ResponseBody
    @RequestMapping(value = HttpAttribute.LOGINCHECKD_JSON)
    public Map<String, Object> adminLogin(HttpSession session,
                                          @RequestParam(value = ParameterAttribute.ACCOUNT, required = false, defaultValue = "") String adminAccount,
                                          @RequestParam(value = ParameterAttribute.PASSWORD, required = false, defaultValue = "") String adminPassword) throws Exception {
        Map<String, Object> returnMap = adminService.managerLogin(adminAccount, adminPassword);
        Integer code = Integer.valueOf(String.valueOf(returnMap.get(ParameterAttribute.CODE)));
        if (code == 0) {
            session.setAttribute(ParameterAttribute.ACCOUNT, adminAccount);
            session.setAttribute(ParameterAttribute.ADMIN_ID, String.valueOf(returnMap.get(ParameterAttribute.ADMIN_ID)));
        }
        return returnMap;
    }

    /**
     * 权限菜单
     * @param session
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = HttpAttribute.MENU_JSON, method = RequestMethod.GET)
    public void AdminMenu(HttpSession session, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter outWriter = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String adminId = String.valueOf(session.getAttribute(ParameterAttribute.ADMIN_ID));
            Map returnMap = adminService.getPermission(adminId);
            outWriter.write(mapper.writeValueAsString(returnMap));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    /**
     * 用户管理
     *
     * @return
     */
    @RequestMapping(value = HttpAttribute.USER_MANAGER_HTML)
    public ModelAndView InInt() {
        return new ModelAndView(ViewPrefixAttribute.USER_MANAGER_PREFIX);
    }


    /**
     * 管理员列表
     * @param response
     * @param searchName
     * @param searchType
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = HttpAttribute.GETALLUSER_JSON)
    public Map<String, Object> getAllUser(
            HttpServletResponse response,
            @RequestParam(value = ParameterAttribute.SEARCH_NAME, required = false, defaultValue = "") String searchName,
            @RequestParam(value = ParameterAttribute.SEARCH_TYPE, required = false, defaultValue = "") String searchType,
            @RequestParam(value = ParameterAttribute.PAGE, required = false, defaultValue = "1") Integer page,
            @RequestParam(value = ParameterAttribute.ROWS, required = false, defaultValue = "10") Integer rows) throws Exception {
        Map<String, Object> querymap = new HashMap<String, Object>();
        response.setContentType("application/json;charset=UTF-8");
        if (!StringUtils.isEmpty(searchName)) {
            querymap = MapUtils.PutLikeMaps(querymap, searchName, searchType,
                    ParameterAttribute.ACCOUNT
            );
        }
        querymap.put(ParameterAttribute.PAGE_START, (page - 1) * rows);
        querymap.put(ParameterAttribute.PAGE_END, rows);
        Map<String, Object> returnMap = null;
        try {
            returnMap = adminService.getAllAdmin(querymap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }


    /**
     * TODO: 2016/9/23   开启状态
     * @param adminId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = HttpAttribute.CHANGE_ROLE_ABLE_JSON)
    public Map<String, Object> changeRoleEnabled(
            @RequestParam(value = ParameterAttribute.ADMIN_ID, required = false, defaultValue = "") String adminId) throws Exception {
        Map<String, Object> returnMap = adminService.changeAdminEnabled(adminId);
        return returnMap;
    }


    /**
     * TODO: 2016/9/23 逻辑删除
     * @param ids
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = HttpAttribute.DELETE_ADMIN_JSON)
    public Map<String, Object> deleteRole(
            @RequestParam(value = "ids", defaultValue = "") String ids) throws Exception {
        Map<String, Object> returnMap = null;
        Map<String, Object> querymap = new HashMap<String, Object>();
        String[] arr = ids.split(",");
        for (int i = 0; i < arr.length; i++) {
            querymap.put(ParameterAttribute.ADMIN_ID, arr[i]);
            returnMap = adminService.deleteAdmin(querymap);
        }
        return returnMap;
    }

    @ResponseBody
    @RequestMapping(value = HttpAttribute.ADD_ADMIN_JSON)
    public Map<String, Object> addAdmin(
            @RequestParam(value = ParameterAttribute.ACCOUNT, required = false, defaultValue = "") String account,
            @RequestParam(value = ParameterAttribute.ADMIN_ID, required = false, defaultValue = "") String adminId,
            @RequestParam(value = ParameterAttribute.PASSWORD, required = false, defaultValue = "") String password,
            @RequestParam(value = ParameterAttribute.IS_ENABLED, defaultValue = "") Integer isEnabled) throws Exception {
        Map<String, Object> querymap = new HashMap<String, Object>();
        querymap.put(ParameterAttribute.ACCOUNT, account);
        querymap.put(ParameterAttribute.PASSWORD, password);
        querymap.put(ParameterAttribute.IS_ENABLED, isEnabled);
        Map<String, Object> returnMap;
        if (adminId.equals("-1")) {
            returnMap = adminService.addAdmin(querymap);
            return returnMap;
        } else {
            querymap.put(ParameterAttribute.ADMIN_ID, adminId);
            returnMap = adminService.updatAdmin(querymap);
            return returnMap;
        }
    }


    @ResponseBody
    @RequestMapping(value = HttpAttribute.SELECT_BY_ADMINID_JSON)

    public Map<String, Object> selectByAdminId(
            @RequestParam(value = ParameterAttribute.ADMIN_ID, required = false, defaultValue = "") String adminId) throws Exception {
        Map<String, Object> returnMap = adminService.selectRoleById(adminId);
        return returnMap;
    }


    @ResponseBody
    @RequestMapping(value = HttpAttribute.GET_ADMIN_ROLES_JSON)
    public Map<String, Object> getUserRole(
            @RequestParam(value = ParameterAttribute.ADMIN_ID, required = false, defaultValue = "") String adminId) {
        Map<String, Object> returnMap = adminService.getAdminRole(adminId);
        return returnMap;
    }


    @ResponseBody
    @RequestMapping(value = HttpAttribute.SAVE_ADMIN_ROLE_JSON)
    public Map<String, Object> saveManagerFunction(@RequestBody Map map) {
        Map returnMap;
        try {
            returnMap = adminService.editAdminRole(map);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);

        }
        return returnMap;
    }


}
