package com.njbd.pt.controller.Role;


import com.njbd.pt.attribute.HttpAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.attribute.ViewPrefixAttribute;
import com.njbd.pt.request.RequestConstant;
import com.njbd.pt.service.Role.RoleService;
import com.njbd.pt.utils.map.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by 李建成
 * ON:  2016/9/22 16:54
 */
@Controller
@RequestMapping(value = HttpAttribute.ADMIN_HTML)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = HttpAttribute.ROLE_MANAGER_HTML)
    public ModelAndView roleManager(HttpSession session) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        return new ModelAndView(ViewPrefixAttribute.ROLE_MANAGER_PREFIX, returnMap);
    }

    /**
     * 获取角色列表
     *
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping(value = HttpAttribute.GET_ALLROLE_JSON)
    @ResponseBody
    public Map<String, Object> getAllRole(
            @RequestParam(value = ParameterAttribute.SEARCH_NAME, required = false, defaultValue = "") String searchName,
            @RequestParam(value = ParameterAttribute.SEARCH_TYPE, required = false, defaultValue = "") String searchType,
            @RequestParam(value = ParameterAttribute.PAGE, required = false, defaultValue = "1") Integer page,
            @RequestParam(value = ParameterAttribute.ROWS, required = false, defaultValue = "10") Integer rows
    ) throws Exception {
        Map<String, Object> querymap = new HashMap<String, Object>();

        if (!StringUtils.isEmpty(searchName)) {
            querymap = MapUtils.PutLikeMaps(querymap, searchName, searchType,
                    ParameterAttribute.NAME,
                    ParameterAttribute.ROLE_DES
            );
        }
        querymap.put(ParameterAttribute.PAGE_START, (page - 1) * rows);
        querymap.put(ParameterAttribute.PAGE_END, rows);
        Map<String, Object> returnMap = roleService.getAllRoles(querymap);
        return returnMap;
    }


    @ResponseBody
    @RequestMapping(value = HttpAttribute.SELECT_ROLEBYID_JSON)
    public Map<String, Object> selectRoleById(
            @RequestParam(value = ParameterAttribute.ROLE_ID, required = false, defaultValue = "") String roleId) throws Exception {
        Map<String, Object> returnMap = roleService.selectRoleById(roleId);
        return returnMap;
    }

    /**
     * 添加或者编辑角色
     */
    @ResponseBody
    @RequestMapping(value = HttpAttribute.ADD_ROLE_JSON)
    public Map<String, Object> addRole(
            @RequestParam(value = ParameterAttribute.ROLE_ID, required = true, defaultValue = "") String roleId,
            @RequestParam(value = ParameterAttribute.NAME, required = false, defaultValue = "") String name,
            @RequestParam(value = ParameterAttribute.ROLE_DES, required = false, defaultValue = "") String description,
            @RequestParam(value = ParameterAttribute.IS_ENABLED, required = true, defaultValue = "") Integer isEnabled) throws Exception {
        Map<String, Object> querymap = new HashMap<String, Object>();
        querymap.put(ParameterAttribute.ROLE_ID, roleId);
        querymap.put(ParameterAttribute.NAME, name);
        querymap.put(ParameterAttribute.ROLE_DES, description);
        querymap.put(ParameterAttribute.IS_ENABLED, isEnabled);
        Map<String, Object> returnMap;
        if (roleId.equals("-1")) {
            returnMap = roleService.addRole(querymap);
            return returnMap;
        } else {
            returnMap = roleService.updateRole(querymap);
            return returnMap;
        }
    }


    /**
     * TODO: 2016/9/23   开启状态
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = HttpAttribute.CHANG_EROLE_ENABLED_JSON)
    public Map<String, Object> changeRoleEnabled(
            @RequestParam(value = ParameterAttribute.ROLE_ID, required = false, defaultValue = "") String roleId) throws Exception {
        Map<String, Object> returnMap = roleService.changeRoleEnabled(roleId);
        return returnMap;
    }


    /**
     * TODO: 2016/9/23 逻辑删除
     * @param ids
     * @return
     * @throws Exception
     */

    @ResponseBody
    @RequestMapping(value = HttpAttribute.DELETE_ROLE_JSON)
    public Map<String, Object> deleteRole(
            @RequestParam(value = "ids", required = true, defaultValue = "") String ids) throws Exception {
        Map<String, Object> returnMap = null;
        Map<String, Object> querymap = new HashMap<String, Object>();
        String[] arr = ids.split(",");
        for (int i = 0; i < arr.length; i++) {
            querymap.put(ParameterAttribute.ROLE_ID, arr[i]);
            returnMap = roleService.deleteRole(querymap);
        }
        return returnMap;
    }

    /**
     * TODO: 2016/9/23 分配权限
     *
     * @param pkId
     * @return
     */
    @RequestMapping(value = HttpAttribute.GET_MANAGER_TBFUNCTION_JSON)
    @ResponseBody
    public Map<String, Object> getManagerTbFunction(
            @RequestParam(value = "pkId", required = false, defaultValue = "") String pkId) {
        Map<String, Object> returnMap = roleService.getUserTbfunction(pkId);
        return returnMap;
    }


    /**
     * TODO: 2016/9/27 保存权限
     *
     * @param map
     * @return
     */
    @RequestMapping(value = HttpAttribute.SAVE_MANAGER_FUNCTION_JSON)
    @ResponseBody
    public Map<String, Object> saveManagerFunction(@RequestBody Map map) {
        Map returnMap;
        try {
            returnMap = roleService.editManagerFunction(map);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }


}
