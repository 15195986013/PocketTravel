package com.njbd.pt.controller.User;

import com.njbd.pt.attribute.HttpAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.attribute.ViewPrefixAttribute;
import com.njbd.pt.service.User.DriverService;
import com.njbd.pt.utils.map.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.njbd.pt.attribute.HttpAttribute.*;

/**
 * Created by 李建成
 *
 * @date 2016/12/5-11:18.on NJBD
 */
@Controller
@RequestMapping(value = HttpAttribute.ADMIN_HTML)
public class DriverController {


    @Autowired
    private DriverService driverService;


    @RequestMapping(value = DRIVER_HTML)

    public ModelAndView DriverViewInIt() {
        return new ModelAndView(ViewPrefixAttribute.DRIVER_MANAGER_PRIFX);
    }


    /**
     * 管理员列表
     *
     * @param response
     * @param searchName
     * @param searchType
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = GETALLDRIVER_JSON)
    public Map<String, Object> getAllDriver(
            HttpServletResponse response,
            @RequestParam(value = ParameterAttribute.SEARCH_NAME, required = false, defaultValue = "") String searchName,
            @RequestParam(value = ParameterAttribute.SEARCH_TYPE, required = false, defaultValue = "") String searchType,
            @RequestParam(value = ParameterAttribute.PAGE, required = false, defaultValue = "1") Integer page,
            @RequestParam(value = ParameterAttribute.ROWS, required = false, defaultValue = "10") Integer rows) throws Exception {
        Map<String, Object> querymap = new HashMap<String, Object>();
        response.setContentType("application/json;charset=UTF-8");
        if (!StringUtils.isEmpty(searchName)) {
            querymap = MapUtils.PutLikeMaps(querymap, searchName, searchType,
                    ParameterAttribute.NAME,
                    ParameterAttribute.DRIVER_INFO
            );
        }
        querymap.put(ParameterAttribute.PAGE_START, (page - 1) * rows);
        querymap.put(ParameterAttribute.PAGE_END, rows);
        Map<String, Object> returnMap = null;
        try {
            returnMap = driverService.getAllDriver(querymap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }


    /**
     * TODO: 2016/9/23  审核状态
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = CANGE_STATUS_JSON)
    public Map<String, Object> changeDriverStatus(
            @RequestParam(value = ParameterAttribute.DRIVER_ID, required = false, defaultValue = "") String DriverId) throws Exception {
        Map<String, Object> returnMap = driverService.changeDriverStatus(DriverId);
        return returnMap;
    }


}
