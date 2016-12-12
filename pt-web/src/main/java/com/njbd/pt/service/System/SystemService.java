package com.njbd.pt.service.System;

import com.njbd.pt.model.SystemInfo;

import java.util.Map;

/**
 * Created by 李建成
 * on 2016/11/28.
 */
public interface SystemService {



    Map selectSystemInfo();

    Map saveSystemInfo(SystemInfo systemInfo);

    Map indexSystemInfo();

}
