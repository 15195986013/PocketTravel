package com.njbd.pt.service.User;

import java.util.Map;

/**
 * Created by 李建成
 *
 * @date 2016/12/5-11:24.on NJBD
 */


public interface DriverService {


    Map<String,Object> getAllDriver(Map<String, Object> querymap);


    Map<String,Object> changeDriverStatus(String DriverId);
}
