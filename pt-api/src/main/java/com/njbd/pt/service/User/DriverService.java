package com.njbd.pt.service.User;

import java.util.Map;

public interface DriverService {
    //获取司机认证信息
    Map getDriverCertificateInfo(String user_id);

    //新增司机
    Map addDriver(String user_id,String name,String identityNumber,String drivingNumber,String vehicleNumber,
                  String plateNumber,String units,String info);
}
