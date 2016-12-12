package com.njbd.pt.service;

import com.njbd.pt.service.User.AdminService;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 李建成
 * on 2016/11/28.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/*.xml"})
public class ServiceTest {


    @Autowired
    private AdminService adminService;
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ServiceTest.class);
    public void TestPrint() {
        adminService.managerLogin("admin","admin");
    }


}
