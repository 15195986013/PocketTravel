package com.njbd.pt.service.User.Impl;

import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.dao.*;
import com.njbd.pt.model.*;
import com.njbd.pt.request.RequestConstant;
import com.njbd.pt.service.User.AdminService;
import com.njbd.pt.utils.map.MapUtils;
import com.njbd.pt.utils.string.UUIDUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * 静态导入
 */




/**
 * Created by 李建成
 * on 2016/11/29.
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleAssigmentMapper roleAssigmentMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionAssigmentMapper permissionAssigmentMapper;
    @Autowired
    private ResourceMapper resourceMapper;


    @Override

    public Map<String, Object> managerLogin(String amdinAccount, String amdinPassword) {
        Map returnMap = new HashMap();
        try {

            Admin admin = adminMapper.selectByAccount(amdinAccount);
            if (admin == null) {
                returnMap = RequestConstant.getRequestcodeDesc(107);
                return returnMap;
            }
            if (amdinPassword.equals(admin.getPassword())) {
                returnMap = RequestConstant.getRequestcodeDesc(0);
                returnMap.put(ParameterAttribute.ADMIN_ID, admin.getId());
                return returnMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getPermission(String adminId) {
        Map returnMap;
        try {
            /**
             *  思路：
             *  1.查询用户有哪些角色
             *  2.存储角色所有权限
             *  3.把权限按照菜单层级分布
             */
            List<PermissionAssigment> permissionAssigmentList = new ArrayList<PermissionAssigment>();//获取权限表记录
            Map queryroleMap = new HashMap();
            queryroleMap.put(ParameterAttribute.ADMIN_ID, adminId);
            List<RoleAssigment> roleAssigments = roleAssigmentMapper.selectByPage(queryroleMap);
            for (RoleAssigment roleAssigment : roleAssigments) {
                HashMap<String, Object> roleMap = new HashMap<String, Object>();
                roleMap.put(ParameterAttribute.PK_ID, roleAssigment.getRoleId());//根据roleId  和isEnabled 查询role ID
                roleMap.put(ParameterAttribute.IS_ENABLED, 0);//筛选启用的角色
                List<Role> roles = roleMapper.selectByPage(roleMap);
                Role role;
                if (roles.size() > 0) {
                    role = roles.get(0);
                    HashMap<String, Object> permissionAssigmentMap = new HashMap<String, Object>();
                    permissionAssigmentMap.put(ParameterAttribute.ROLE_ID, role.getId());
                    permissionAssigmentList = permissionAssigmentMapper.selectByPage(permissionAssigmentMap);
                }
            }
            Set<String> ResoustIds = new HashSet<String>();//存储该角色所有权限
            for (PermissionAssigment permissionAssigment : permissionAssigmentList) {
                ResoustIds.add(permissionAssigment.getResourceId());
                ResoustIds.add(resourceMapper.selectByPrimaryKey(permissionAssigment.getResourceId()).getParentId());
            }
            Map querymap = new HashMap();
            //一级菜单 所有 parentId为 0 的 模板组
            querymap.put(ParameterAttribute.PARENT_ID, "0");
            List<Resource> resourceListOne = resourceMapper.selectByPage(querymap);//一级列表
            List onemodelList = new ArrayList<Map>();//存放一级菜单封装集合
            for (Resource resourceOne : resourceListOne) {
                if (ResoustIds.contains(resourceOne.getId())) {//该角色是否含有该一级权限
                    Map oneModelMap = new HashedMap();
                    querymap.put(ParameterAttribute.PARENT_ID, resourceOne.getId());
                    List<Resource> resourceListTwo = resourceMapper.selectByPage(querymap);//二级列表
                    List<Map> twomodellist = new ArrayList();//存放一级菜单封装集合
                    for (Resource resourcetwo : resourceListTwo) {
                        Map twoModelMap = new HashedMap();
                        if (ResoustIds.contains(resourcetwo.getId())) {//该角色是否含有该二级权限
                            twoModelMap.put(ParameterAttribute.PK_ID, resourcetwo.getId());
                            twoModelMap.put(ParameterAttribute.NAME, resourcetwo.getName());
                            twoModelMap.put(ParameterAttribute.URL, resourcetwo.getUrl());
                            twomodellist.add(twoModelMap);
                        }
                    }
                    oneModelMap.put(ParameterAttribute.PK_ID, resourceOne.getId());
                    oneModelMap.put(ParameterAttribute.NAME, resourceOne.getName());
                    oneModelMap.put(ParameterAttribute.URL, resourceOne.getUrl());
                    oneModelMap.put(ParameterAttribute.MENUS, twomodellist);
                    onemodelList.add(oneModelMap);
                }
            }
            returnMap = RequestConstant.getRequestcodeDesc(0);
            returnMap.put(ParameterAttribute.MENUS, onemodelList);
            logger.info("模板分组菜单已成功获取！");
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
            logger.info("模板分组菜单获取失败！", e.toString());
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> selectAdminById(String adminId) {

        Map returnMap;
        try {
            Admin admin = adminMapper.selectByPrimaryKey(adminId);
            MapUtils mapUtils = new MapUtils();
            returnMap = mapUtils.getReturnMapValues(
                    ParameterAttribute.ADMIN_ID, admin.getId(),
                    ParameterAttribute.ACCOUNT, admin.getAccount(),
                    ParameterAttribute.PASSWORD, admin.getPassword()
            );
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据用户Id获取用户信息失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> changePassword(Admin admin) {
        Map returnMap = new HashMap();
        try {
            Admin newAdmin = adminMapper.selectByPrimaryKey(admin.getId());
            newAdmin.setPassword(admin.getPassword());
            adminMapper.updateByPrimaryKey(newAdmin);
            returnMap = RequestConstant.getRequestcodeDesc(0);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改密码失败", e);
            returnMap.put("msg", "密码修改失败");
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getAllAdmin(Map<String, Object> map) {
        Map returnMap;
        try {
            List<Object> returnUserList = new ArrayList<Object>();
            map.put(ParameterAttribute.DELFLAG, 0);
            List<Admin> adminList = adminMapper.selectByPage(map);
            for (Admin admin : adminList) {
                //根据admin id 查询 分配的角色
                HashMap queryroleMap = new HashMap();
                queryroleMap.put(ParameterAttribute.ADMIN_ID, admin.getId());
                String roles = "";
                List<RoleAssigment> assigmentList = roleAssigmentMapper.selectByPage(queryroleMap);
                for (RoleAssigment roleAssigment : assigmentList) {
                    roles = roles + roleMapper.selectByPrimaryKey(roleAssigment.getRoleId()).getName() + "  ";
                }
                MapUtils mapUtils = new MapUtils();
                Map remap = mapUtils.getReturnMapValues(
                        ParameterAttribute.NAME, roles,
                        ParameterAttribute.ADMIN_ID, admin.getId(),
                        ParameterAttribute.ACCOUNT, admin.getAccount(),
                        ParameterAttribute.IS_ENABLED, admin.getIsEnabled(),
                        ParameterAttribute.UPDATETIME, admin.getUpdateTime()
                );
                returnUserList.add(remap);
            }
            returnMap = RequestConstant.getRequestcodeDesc(0);
            returnMap.put(ParameterAttribute.PAGE_ROWS, returnUserList);
            map.remove(ParameterAttribute.PAGE_START);
            map.remove(ParameterAttribute.PAGE_END);
            returnMap.put(ParameterAttribute.PAGE_TOTAl, adminMapper.selectByPage(map).size());
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取用户列表失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> changeAdminEnabled(String adminId) {
        Map returnMap;
        try {
            Admin admin = adminMapper.selectByPrimaryKey(adminId);
            Admin superAdmin = adminMapper.selectByPage(null).get(0);
            if (admin.getId().equals(superAdmin.getId())) {
                returnMap = RequestConstant.getRequestcodeDesc(141);
                return returnMap;
            } else {
                if (admin.getIsEnabled() == 1) {
                    admin.setIsEnabled(0);
                    returnMap = MapUtils.getRequestMapMsg(0, "用户已启用");
                } else {
                    admin.setIsEnabled(1);
                    returnMap = MapUtils.getRequestMapMsg(0, "用户已经禁用");
                }
                admin.setUpdateTime(new Date());
                adminMapper.updateByPrimaryKeySelective(admin);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("开启用户状态失败", e);
            return RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> deleteAdmin(Map<String, Object> querymap) {
        Map returnMap;
        try {
            String adminId = String.valueOf(querymap.get(ParameterAttribute.ADMIN_ID));
            Admin admin = adminMapper.selectByPrimaryKey(adminId);
            if (admin.getId().equals(adminMapper.selectByPage(null).get(0).getId())) {
                returnMap = MapUtils.getRequestMapMsg(1, "您没有此操作权限");
            } else {
                if (admin.getDeleteFlag() == 0) {
                    admin.setDeleteFlag(1);
                    adminMapper.updateByPrimaryKey(admin);
                }
                returnMap = MapUtils.getRequestMapMsg(0, "请求成功");
            }
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除用户状态失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> addAdmin(Map<String, Object> querymap) {
        Map returnMap;
        try {
            String Adminaccount = String.valueOf(querymap.get(ParameterAttribute.ACCOUNT));
            if (adminMapper.selectByAccount(Adminaccount) != null) {
                returnMap = RequestConstant.getRequestcodeDesc(111);
                return returnMap;
            }
            String Adminpassword = String.valueOf(querymap.get(ParameterAttribute.PASSWORD));
            Integer isEnabled = Integer.valueOf(String.valueOf(querymap.get(ParameterAttribute.IS_ENABLED)));
            Admin admin = new Admin();
            admin.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
            admin.setAccount(Adminaccount);
            admin.setPassword(Adminpassword);
            admin.setIsEnabled(isEnabled);
            admin.setDeleteFlag(0);
            admin.setCreateTime(new Date());
            admin.setUpdateTime(new Date());
            adminMapper.insert(admin);
            returnMap = RequestConstant.getRequestcodeDesc(0);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加用户失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> updatAdmin(Map<String, Object> querymap) {
        Map returnMap;
        try {
            String adminId = String.valueOf(querymap.get(ParameterAttribute.ADMIN_ID));
            Admin admin = adminMapper.selectByPrimaryKey(adminId);
            admin.setAccount(String.valueOf(querymap.get(ParameterAttribute.ACCOUNT)));
            admin.setPassword(String.valueOf(querymap.get(ParameterAttribute.PASSWORD)));
            admin.setIsEnabled(Integer.valueOf(String.valueOf(querymap.get(ParameterAttribute.IS_ENABLED))));
            admin.setUpdateTime(new Date());
            adminMapper.updateByPrimaryKey(admin);
            returnMap = RequestConstant.getRequestcodeDesc(0);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改角色失败", e);
            return RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> selectRoleById(String adminId) {
        Map returnMap = new HashMap();
        try {
            Admin admin = adminMapper.selectByPrimaryKey(adminId);
            if (admin != null) {
                MapUtils mapUtils = new MapUtils();
                returnMap = mapUtils.getReturnMapValues(
                        ParameterAttribute.ADMIN_ID, admin.getId(),
                        ParameterAttribute.ACCOUNT, admin.getAccount(),
                        ParameterAttribute.PASSWORD, admin.getPassword(),
                        ParameterAttribute.IS_ENABLED, admin.getIsEnabled()
                );
            }
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据角色Id获取用户信息失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getAdminRole(String adminId) {
        Map returnMap;
        try {
            List<Map> roleMapList = new ArrayList<Map>();
            List<Map> totalRoleMapList = new ArrayList<Map>();//有根节点的树
            HashMap<String, Object> roleMap = new HashMap<String, Object>();
            roleMap.put(ParameterAttribute.IS_ENABLED, 0);
            roleMap.put(ParameterAttribute.DELFLAG, 0);
            List<Role> roleList = roleMapper.selectByPage(roleMap); // 获取全部权限
            HashMap<String, Object> assigmentMap = new HashMap<String, Object>();
            assigmentMap.put(ParameterAttribute.ADMIN_ID, adminId);
            List<RoleAssigment> roleAssigments = roleAssigmentMapper.selectByPage(assigmentMap);
            Map totalMap = new HashMap();
            for (Role role : roleList) {
                Map modelMap = new HashMap();
                modelMap.put(ParameterAttribute.PK_ID, role.getId());
                modelMap.put(ParameterAttribute.TEXT, role.getName());
                modelMap.put(ParameterAttribute.CHECKED, false);
                modelMap.put(ParameterAttribute.PARENT_ID, 0);
                for (RoleAssigment roleAssigment : roleAssigments) {
                    if (roleAssigment.getRoleId().equals(role.getId())) {
                        modelMap.put(ParameterAttribute.CHECKED, true);
                    }
                }
                roleMapList.add(modelMap);
            }
            //角色列表根目录
            totalMap.put(ParameterAttribute.PK_ID, 0);
            totalMap.put(ParameterAttribute.TEXT, "角色列表");
            totalMap.put(ParameterAttribute.CHILDREN, roleMapList);
            totalMap.put(ParameterAttribute.STATUS, "opened");
            totalRoleMapList.add(totalMap);
            returnMap = RequestConstant.getRequestcodeDesc(0);
            returnMap.put(ParameterAttribute.DATA, totalRoleMapList);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map editAdminRole(Map map) {
        Map<String, Object> returnMap;
        try {
            List<String> saveRoleList = new ArrayList<String>(); //要保存的角色id列表
            List<String> AdminRoleList = new ArrayList<String>(); //原来的角色id列表
            List<Role> RoleList = roleMapper.selectByPage(null);     //所有角色
            String adminId = (String) map.get(ParameterAttribute.TYPEID);
            List<Map> saveRoleMapList = (List<Map>) map.get(ParameterAttribute.DATA); //将要保存的角色
            for (Map roleMap : saveRoleMapList) {
                saveRoleList.add(String.valueOf(roleMap.get(ParameterAttribute.PK_ID)));  //将要保存的角色放在一个权限list里
            }
            HashMap<String, Object> assigmentMap = new HashMap<String, Object>();
            assigmentMap.put(ParameterAttribute.ADMIN_ID, adminId);
            List<RoleAssigment> adminAssigmentList = roleAssigmentMapper.selectByPage(assigmentMap);
            for (RoleAssigment adminAssigment : adminAssigmentList) {
                AdminRoleList.add(String.valueOf(adminAssigment.getRoleId()));  // 原来的角色
            }
            for (Role role : RoleList) {
                String roleId = String.valueOf(role.getId());
                if (saveRoleList.contains(roleId)) {      //将要开通的权限按钮是否存在所有的权限按钮中
                    if (!AdminRoleList.contains(roleId)) {      //之前的权限按钮是否存有了，有则不插入，没有则插入一个新的权限记录
                        RoleAssigment userAssigment = new RoleAssigment();
                        userAssigment.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
                        userAssigment.setUserId(adminId);
                        userAssigment.setRoleId(roleId);
                        userAssigment.setCreateTime(new Date());
                        roleAssigmentMapper.insert(userAssigment);
                    }
                } else {
                    if (AdminRoleList.contains(roleId)) {
                        Map delUserAssigmentMap = new HashMap();
                        delUserAssigmentMap.put(ParameterAttribute.USER_ID, adminId);
                        delUserAssigmentMap.put(ParameterAttribute.ROLE_ID, roleId);
                        roleAssigmentMapper.deleteByMap(delUserAssigmentMap);
                    }
                }
            }
            returnMap = MapUtils.getRequestMapMsg(0, "用户角色已修改");
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

}
