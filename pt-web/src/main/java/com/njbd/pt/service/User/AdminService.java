package com.njbd.pt.service.User;

import com.njbd.pt.model.Admin;

import java.util.Map;

/**
 * Created by 李建成
 * on 2016/11/29.
 */
public interface AdminService {

    //管理员登录
    Map<String, Object> managerLogin(String managerAccount, String managerPassword);

    //管理模块
    Map<String, Object> getPermission(String managerId);

    //查询管理员
    Map<String, Object> selectAdminById(String adminId);

    //修改密码
    Map<String, Object> changePassword(Admin admin);

    //获取管理员列表
    Map<String, Object> getAllAdmin(Map<String, Object> map);

    //修改管理员启用状态
    Map<String, Object> changeAdminEnabled(String adminId);

    //删除角色
    Map<String, Object> deleteAdmin(Map<String, Object> querymap);

    //添加角色
    Map<String, Object> addAdmin(Map<String, Object> querymap);

    //修改角色
    Map<String, Object> updatAdmin(Map<String, Object> querymap);

    //查询角色
    Map<String, Object> selectRoleById(String adminId);

    // 根据adminId 查询AdminRole
    Map<String, Object> getAdminRole(String adminId);

    //编辑权限
    Map editAdminRole(Map map);
}
