package com.njbd.pt.service.Com;


import java.util.Map;

public interface PostService {
    //发布网约车
    Map addPost(String user_id, String start_postion, String end_postion, Double start_lon, Double start_lat,
                Double end_lon, Double end_lat, String pubDate, String appraisal);

    //取消发布
    Map deletePost(String id);

    //抢单
    Map receivePost(String id);

    //获取行程列表
    Map getTripList(Map<String, Object> querymap);

    //获取乘载记录
    Map getRecordList(Map<String, Object> querymap);

}
