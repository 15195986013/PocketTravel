package com.njbd.pt.service.Role;

import java.util.Map;

/**
 * Created by 李建成
 * ON:  2016/9/22 18:01
 */
public interface RoleService {
    Map<String,Object> getAllRoles(Map<String, Object> querymap);

    Map<String,Object> addRole(Map<String, Object> querymap);

    Map<String,Object> updateRole(Map<String, Object> querymap);

    Map<String,Object> selectRoleById(String roleId);

    Map<String,Object> changeRoleEnabled(String roleId);

    Map<String,Object> deleteRole(Map<String, Object> querymap);

    Map<String,Object> getUserTbfunction(String roleId);

    Map editManagerFunction(Map map);

    boolean managerInRole(String adminId, String resourceId);
}
