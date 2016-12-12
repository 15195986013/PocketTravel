package com.njbd.pt.service.Version;

import java.util.Map;

/**
 * Created by 李建成
 * ON:  2016/10/19 17:00
 */

public interface VersionService {


    Map<String,Object> getAllVersion(Map<String, Object> querymap);

    Map<String,Object> addVersion(Map<String, Object> querymap);

    Map<String,Object> updateVersion(Map<String, Object> querymap);

    Map<String,Object> selectVersionById(String versionId);

    Map<String,Object> deleteVersion(Map<String, Object> querymap);
}
