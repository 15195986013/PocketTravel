package com.njbd.pt.service.Role.Impl;


import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.dao.PermissionAssigmentMapper;
import com.njbd.pt.dao.ResourceMapper;
import com.njbd.pt.dao.RoleAssigmentMapper;
import com.njbd.pt.dao.RoleMapper;
import com.njbd.pt.model.PermissionAssigment;
import com.njbd.pt.model.Resource;
import com.njbd.pt.model.Role;
import com.njbd.pt.model.RoleAssigment;
import com.njbd.pt.request.RequestConstant;
import com.njbd.pt.service.Role.RoleService;
import com.njbd.pt.utils.map.MapUtils;
import com.njbd.pt.utils.string.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 李建成
 * ON:  2016/9/22 18:01
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);


    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleAssigmentMapper roleAssigmentMapper;

    @Autowired
    private PermissionAssigmentMapper permissionAssigmentMapper;
    @Autowired
    private ResourceMapper resourceMapper;


    @Override
    public Map<String, Object> getAllRoles(Map<String, Object> map) {
        Map returnMap;
        try {
            /**
             *  查询已经启用并没有删除的角色
             */
            map.put(ParameterAttribute.DELFLAG, 0);
            List<Role> roleList = roleMapper.selectByPage(map);
            List<Object> roles = new ArrayList<Object>();
            for (Role role : roleList) {
                MapUtils mapUtils = new MapUtils();
                Map remap = mapUtils.getReturnMapValues(
                        ParameterAttribute.ROLE_ID, role.getId(),
                        ParameterAttribute.NAME, role.getName(),
                        ParameterAttribute.ROLE_DES, role.getDescription(),
                        ParameterAttribute.IS_ENABLED, role.getIsEnabled(),
                        ParameterAttribute.CREATETIME, role.getCreateTime(),
                        ParameterAttribute.UPDATETIME, role.getUpdateTime()
                );
                roles.add(remap);
            }
            returnMap = RequestConstant.getRequestcodeDesc(0);
            returnMap.put(ParameterAttribute.PAGE_ROWS, roles);
            map.remove(ParameterAttribute.PAGE_START);
            map.remove(ParameterAttribute.PAGE_END);
            returnMap.put(ParameterAttribute.PAGE_TOTAl, roleMapper.selectByPage(map).size());
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取角色列表失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> addRole(Map<String, Object> querymap) {
        Map returnMap;
        try {
            String roleName = String.valueOf(querymap.get(ParameterAttribute.NAME));
            String roleDesc = String.valueOf(querymap.get(ParameterAttribute.ROLE_DES));
            Integer isEnabled = Integer.valueOf(String.valueOf(querymap.get(ParameterAttribute.IS_ENABLED)));
            Role role = new Role();
            role.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
            role.setName(roleName);
            role.setDescription(roleDesc);
            role.setIsEnabled(isEnabled);
            role.setDeleteFlag(0);
            role.setCreateTime(new Date());
            role.setUpdateTime(new Date());
            roleMapper.insert(role);
            returnMap = RequestConstant.getRequestcodeDesc(0);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加角色失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }


    @Override
    public Map<String, Object> selectRoleById(String roleId) {
        Map returnMap = new HashMap();
        try {
            Role role = roleMapper.selectByPrimaryKey(roleId);
            if (role != null) {
                returnMap = RequestConstant.getRequestcodeDesc(0);
                if (("").equals(role.getId())) {
                    returnMap.put(ParameterAttribute.ROLE_ID, "");
                } else {
                    returnMap.put(ParameterAttribute.ROLE_ID, role.getId());
                }
                if (("").equals(role.getName())) {
                    returnMap.put(ParameterAttribute.NAME, "");
                } else {
                    returnMap.put(ParameterAttribute.NAME, role.getName());
                }
                if (("").equals(role.getDescription())) {
                    returnMap.put(ParameterAttribute.ROLE_DES, "");
                } else {
                    returnMap.put(ParameterAttribute.ROLE_DES, role.getDescription());
                }
                if (("").equals(role.getIsEnabled())) {
                    returnMap.put(ParameterAttribute.IS_ENABLED, "");
                } else {
                    returnMap.put(ParameterAttribute.IS_ENABLED, role.getIsEnabled());
                }
            }
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据角色Id获取角色信息失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> changeRoleEnabled(String roleId) {
        Map returnMap = new HashMap();
        try {
            Role superRole = roleMapper.selectByPage(null).get(0);
            if (roleId.equals(superRole.getId()) && superRole != null) {
                returnMap = RequestConstant.getRequestcodeDesc(151);
                return returnMap;
            } else {
                Role role = roleMapper.selectByPrimaryKey(roleId);
                if (role.getIsEnabled() == 1) {
                    role.setIsEnabled(0);
                    returnMap.put(ParameterAttribute.CODE, 0);
                    returnMap.put(ParameterAttribute.MSG, "权限已经开启");
                } else {
                    role.setIsEnabled(1);
                    returnMap.put(ParameterAttribute.CODE, 0);
                    returnMap.put(ParameterAttribute.MSG, "权限已经关闭");
                }
                role.setUpdateTime(new Date());
                roleMapper.updateByPrimaryKey(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("是否开启角色失败", e);
            return RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> deleteRole(Map<String, Object> querymap) {
        Map returnMap = new HashMap();
        try {
            String roleId = String.valueOf(querymap.get(ParameterAttribute.ROLE_ID));
            Role role = roleMapper.selectByPrimaryKey(roleId);
            if (role.getId().equals(roleMapper.selectByPage(null).get(0).getId())) {
                returnMap.put(ParameterAttribute.MSG, "您没有此操作权限");
                returnMap.put(ParameterAttribute.CODE, 1);
            } else {
                if (role.getDeleteFlag() == 0) {
                    role.setDeleteFlag(1);
                    roleMapper.updateByPrimaryKey(role);
                }
                returnMap.put(ParameterAttribute.CODE, 0);
            }
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除角色失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> updateRole(Map<String, Object> querymap) {
        Map returnMap;
        try {
            String roleId = String.valueOf(querymap.get(ParameterAttribute.ROLE_ID));
            Role role = roleMapper.selectByPrimaryKey(roleId);
            role.setName(String.valueOf(querymap.get(ParameterAttribute.NAME)));
            role.setDescription(String.valueOf(querymap.get(ParameterAttribute.ROLE_DES)));
            role.setIsEnabled(Integer.valueOf(String.valueOf(querymap.get(ParameterAttribute.IS_ENABLED))));
            role.setUpdateTime(new Date());
            roleMapper.updateByPrimaryKey(role);
            returnMap = RequestConstant.getRequestcodeDesc(0);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改角色失败", e);
            return RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getUserTbfunction(String roleId) {
        Map returnMap;
        try {
            List<String> permissionAssigmentListId = new ArrayList<String>();//用来存放原来permissionAssigment的resId
            HashMap queryRoleMap = new HashMap();
            queryRoleMap.put(ParameterAttribute.ROLE_ID, roleId);
            List<PermissionAssigment> permissionAssigments = permissionAssigmentMapper.selectByPage(queryRoleMap);
            for (PermissionAssigment permissionAssigment : permissionAssigments) {
                if (!permissionAssigmentListId.contains(permissionAssigment.getRoleId())) {//过滤掉重复的数据
                    permissionAssigmentListId.add(permissionAssigment.getResourceId());
                }
            }
            List<Resource> oneResource = new ArrayList<Resource>(); //记录一级模块
            List<Resource> twoResource = new ArrayList<Resource>(); //记录二级模块
            List<Resource> resourceList = resourceMapper.selectByPage(null);
            for (Resource resource : resourceList) {
                if (("none").equals(resource.getUrl()) || ("").equals(resource.getUrl())) {
                    oneResource.add(resource);
                } else {
                    twoResource.add(resource);
                }
            }
            List<Map> models = new ArrayList<Map>();
            for (Resource resource : oneResource) {
                Map modelMap = new HashMap();
                modelMap.put(ParameterAttribute.PK_ID, resource.getId());
                modelMap.put(ParameterAttribute.TEXT, resource.getName());
                List<Map> functions = new ArrayList<Map>();
                for (Resource tworesource : twoResource) {
                    if (tworesource.getParentId().equals(resource.getId())) {
                        Map functionMap = new HashMap();
                        functionMap.put(ParameterAttribute.PK_ID, tworesource.getId());
                        functionMap.put(ParameterAttribute.TEXT, tworesource.getName());
                        if (permissionAssigmentListId.contains(tworesource.getId())) {//permissionAssigment的resId和resource里面的pkId作匹配
                            functionMap.put(ParameterAttribute.CHECKED, true);
                        } else {
                            functionMap.put(ParameterAttribute.CHECKED, false);
                        }
                        functions.add(functionMap);
                    }
                }
                modelMap.put(ParameterAttribute.CHILDREN, functions);
                modelMap.put(ParameterAttribute.STATUS, "closed");
                models.add(modelMap);
            }
            returnMap = RequestConstant.getRequestcodeDesc(0);
            returnMap.put("msg", "获取用户成功");
            returnMap.put("data", models);
            return returnMap;
        } catch (Exception e) {
            returnMap = RequestConstant.getRequestcodeDesc(100);
            logger.debug(e.toString());
            e.printStackTrace();
        }
        return returnMap;
    }

    @Override
    public Map editManagerFunction(Map map) {
        Map<String, Object> returnMap;
        try {
            String role_id = (String) map.get(ParameterAttribute.TYPEID);//泛型转换
            HashMap permissionAssigmentmap = new HashMap();
            permissionAssigmentmap.put(ParameterAttribute.ROLE_ID, role_id);
            List<PermissionAssigment> permissionAssigmentList = permissionAssigmentMapper.selectByPage(permissionAssigmentmap);
            List<Map> saveFunctionMapList = (List<Map>) map.get(ParameterAttribute.DATA); //将要保存的权限的resId列表
            /**
             * 权限修改
             * 1.遍历删除
             * 2.批量保存
             * */
            for (PermissionAssigment permissionAssigment : permissionAssigmentList) {
                permissionAssigmentMapper.deleteByPrimaryKey(permissionAssigment.getId());
            }
            for (Map saveFunctionMap : saveFunctionMapList) {
                PermissionAssigment permissionAssigment = new PermissionAssigment();
                permissionAssigment.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
                permissionAssigment.setResourceId((String) saveFunctionMap.get(ParameterAttribute.PK_ID)); // 新修改的权限
                permissionAssigment.setRoleId(role_id); //当前角色
                permissionAssigment.setCreateTime(new Date());
                permissionAssigmentMapper.insertSelective(permissionAssigment);
            }
            returnMap = RequestConstant.getRequestcodeDesc(0);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改角色权限失败", e);
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }

    @Override
    public boolean managerInRole(String adminId, String resourceId) {
        Boolean flag = false;
        HashMap queryMap = new HashMap();
        queryMap.put(ParameterAttribute.ADMIN_ID, adminId);
        List<RoleAssigment> roleAssigments = roleAssigmentMapper.selectByPage(queryMap);
        for (RoleAssigment roleAssigment : roleAssigments) {
            queryMap.put(ParameterAttribute.ROLE_ID, roleAssigment.getId());
            queryMap.put(ParameterAttribute.RESOURCE_ID, resourceId);
            List<PermissionAssigment> permissionAssigment = permissionAssigmentMapper.selectByPage(queryMap);
            if (permissionAssigment.size() > 0) {
                flag = true;
            }
        }
        return flag;
    }
}
