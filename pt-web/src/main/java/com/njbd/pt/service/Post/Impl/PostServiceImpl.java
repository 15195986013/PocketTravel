package com.njbd.pt.service.Post.Impl;

import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.dao.PostMapper;
import com.njbd.pt.dao.UserInfoMapper;
import com.njbd.pt.model.Post;
import com.njbd.pt.model.UserInfo;
import com.njbd.pt.request.RequestConstant;
import com.njbd.pt.service.Post.PostService;
import com.njbd.pt.utils.map.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李建成
 *
 * @date 2016/12/2-15:48.on NJBD
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Map<String, Object> getAllPost(Map<String, Object> querymap) {
        Map returnMap = new HashMap();
        try {
            List<Post> posts = postMapper.selectByPage(querymap);
            List<Map> Returnlist = new ArrayList<Map>();
            for (Post post : posts) {
                String UserName = "";
                if (StringUtils.isNotEmpty(post.getUserId())) {
                    UserInfo userInfo = userInfoMapper.selectByPrimaryUserId(post.getUserId());
                    UserName = userInfo.getNickname() == null || "null".equals(userInfo.getNickname()) ? "" : userInfo.getNickname();
                }
                Map paraMap = MapUtils.BeanToMap(post);
                paraMap.put(ParameterAttribute.NAME, UserName);
                Returnlist.add(paraMap);
            }
            returnMap = RequestConstant.getRequestcodeDesc(0);
            MapUtils.MapRemovePageSize(returnMap, querymap, Returnlist);
            List<Post> list = postMapper.selectByPage(querymap);
            returnMap.put(ParameterAttribute.PAGE_TOTAl, list.size());
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }
}
