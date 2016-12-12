package com.njbd.pt.controller.Com;
/**
 *
 */


import com.njbd.pt.attribute.InterfaceAddressAttribute;
import com.njbd.pt.attribute.RequestConstant;
import com.njbd.pt.utils.file.FileUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(value = InterfaceAddressAttribute.COMMON)
public class UploadController {
    private final static Logger logger = LoggerFactory.getLogger(UploadController.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");


    @RequestMapping(value = "upload.action")
    @ResponseBody
    public Map uploadFile(
            HttpServletResponse response,
            HttpServletRequest request, Integer width, Integer height,
            @RequestParam(value = "file") MultipartFile file
    ) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        Map returnMap;
        try {
            FileUtil fileUtil = new FileUtil();
            returnMap = fileUtil.upload(file, width, height, "/upload/photo/" + dateFormat.format(new Date()), request);
        } catch (Exception e) {
            returnMap = RequestConstant.getRequestcodeDesc(100);
        }
        return returnMap;
    }


}