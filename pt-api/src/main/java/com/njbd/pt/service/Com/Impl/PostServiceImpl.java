package com.njbd.pt.service.Com.Impl;

import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.attribute.RequestConstant;
import com.njbd.pt.dao.PostMapper;
import com.njbd.pt.dao.UserInfoMapper;
import com.njbd.pt.model.Post;
import com.njbd.pt.model.UserInfo;
import com.njbd.pt.service.Com.PostService;
import com.njbd.pt.utils.string.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.njbd.pt.attribute.RequestConstant.getRequestcodeDesc;


@Service
public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    public Map addPost(String user_id, String start_postion, String end_postion, Double start_lon, Double start_lat,
                       Double end_lon, Double end_lat, String pubDate, String appraisal) {
        Map returnMap = new HashMap();
        try {
            Post post = new Post();
            post.setUserId(user_id);
            post.setAppraisal(Float.parseFloat(appraisal));
            post.setPubDate(pubDate);
            post.setType(0);
            post.setState(0);
            post.setUpdateTime(new Date());
            post.setId(UUIDUtils.getSimpleUUID(UUIDUtils.getUUID()));
            post.setStartPostion(start_postion);
            post.setStartLon(start_lon);
            post.setStartLat(start_lat);
            post.setEndPostion(end_postion);
            post.setEndLon(end_lon);
            post.setEndLat(end_lat);
            post.setCreateTime(new Date());
            int n = postMapper.insert(post);
            if (n == 1) {
                returnMap = RequestConstant.getRequestcodeDesc(0);
                returnMap.put(ParameterAttribute.DATA, null);
            } else {
                returnMap = RequestConstant.getRequestcodeDesc(105);
                returnMap.put(ParameterAttribute.DATA, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA, null);
            return returnMap;
        }
        return returnMap;
    }

    public Map deletePost(String id) {
        Map returnMap;
        logger.info("取消网约车发布开始：");
        try {
            int n = postMapper.deleteByPrimaryKey(id);
            if (n == 1) {
                returnMap = RequestConstant.getRequestcodeDesc(0);
                returnMap.put(ParameterAttribute.DATA, null);
            } else {
                returnMap = RequestConstant.getRequestcodeDesc(106);
                returnMap.put(ParameterAttribute.DATA, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA, null);
            return returnMap;
        }
        return returnMap;
    }

    public Map receivePost(String id) {
        Map returnMap;
        logger.info("抢单开始：");
        try {
            int n = 0;
            Post post = postMapper.selectByPrimaryKey(id);
            if (post != null && post.getState() == 0) {
                post.setState(1);
                n = postMapper.updateByPrimaryKeySelective(post);
            } else {
                logger.debug("未找到发布订单");
            }
            if (n == 1) {
                returnMap = RequestConstant.getRequestcodeDesc(0);
                returnMap.put(ParameterAttribute.DATA, null);
            } else {
                returnMap = RequestConstant.getRequestcodeDesc(107);
                returnMap.put(ParameterAttribute.DATA, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = RequestConstant.getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA, null);
            return returnMap;
        }
        return returnMap;
    }

    public Map getTripList(Map<String, Object> querymap) {
        Map returnMap;
        List<Map> Returnlist = new ArrayList<Map>();
        try {
            List<Post> posts = postMapper.selectByPage(querymap);
            Map paraMap = new HashMap();
            for (Post post : posts) {
                String userName = "";
                String phone = "";
                String avatar = "";
                if (StringUtils.isNotEmpty(post.getUserId())) {
                    UserInfo userInfo = userInfoMapper.selectByPrimaryUserId(post.getUserId());
                    if (userInfo != null) {
                        userName = userInfo.getNickname();
                        phone = userInfo.getPhone();
                        avatar = userInfo.getAvatar();
                    }
                }
                paraMap.put(ParameterAttribute.NICKNAME, userName);
                paraMap.put(ParameterAttribute.PHONE, phone);
                paraMap.put(ParameterAttribute.AVATAR, avatar);
                paraMap.put(ParameterAttribute.POSTID, post.getId());
                paraMap.put(ParameterAttribute.START, post.getStartPostion());
                paraMap.put(ParameterAttribute.END, post.getEndPostion());
                paraMap.put(ParameterAttribute.TIME, new SimpleDateFormat("yyyy-MM-dd").format(post.getPubDate()));
                paraMap.put(ParameterAttribute.VALUATION, post.getAppraisal());
                Returnlist.add(paraMap);
            }
            returnMap = RequestConstant.getRequestcodeDesc(0);
            returnMap.put(ParameterAttribute.DATA, Returnlist);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA, null);
        }
        return returnMap;
    }

    public Map getRecordList(Map<String, Object> querymap) {
        Map returnMap;
        List<Map> Returnlist = new ArrayList<Map>();
        try {
            List<Post> posts = postMapper.selectByPage(querymap);
            if (posts.size() > 0) {
                for (Post post : posts) {
                    String userName = "";
                    String phone = "";
                    String avatar = "";
                    if (StringUtils.isNotEmpty(post.getUserId())) {
                        UserInfo userInfo = userInfoMapper.selectByPrimaryUserId(post.getUserId());
                        if (userInfo != null) {
                            userName = userInfo.getNickname();
                            phone = userInfo.getPhone();
                            avatar = userInfo.getAvatar();
                        }
                    }
                    Map paraMap = new HashMap();
                    paraMap.put(ParameterAttribute.NICKNAME, userName);
                    paraMap.put(ParameterAttribute.PHONE, phone);
                    paraMap.put(ParameterAttribute.AVATAR, avatar);
                    paraMap.put(ParameterAttribute.POSTID, post.getId());
                    paraMap.put(ParameterAttribute.START, post.getStartPostion());
                    paraMap.put(ParameterAttribute.END, post.getEndPostion());
                    paraMap.put(ParameterAttribute.TIME, new SimpleDateFormat("yyyy-MM-dd").format(post.getPubDate()));
                    Returnlist.add(paraMap);
                }
                returnMap = RequestConstant.getRequestcodeDesc(0);
            } else {
                returnMap = RequestConstant.getRequestcodeDesc(112);
            }
            returnMap.put(ParameterAttribute.DATA, Returnlist);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap = getRequestcodeDesc(1001);
            returnMap.put(ParameterAttribute.DATA, Returnlist);
            return returnMap;
        }
        return returnMap;
    }

}
