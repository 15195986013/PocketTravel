package com.njbd.pt.controller.Post;

import com.njbd.pt.attribute.HttpAttribute;
import com.njbd.pt.attribute.ParameterAttribute;
import com.njbd.pt.attribute.ViewPrefixAttribute;
import com.njbd.pt.service.Post.PostService;
import com.njbd.pt.utils.map.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李建成
 *
 * @date 2016/12/2-15:45.on NJBD
 */

@Controller
@RequestMapping(value = HttpAttribute.ADMIN_HTML)
public class PostContrller {

    @Autowired
    private PostService postService;


    @RequestMapping(value = HttpAttribute.POST_MANAGER_HTML)
    public ModelAndView InInt() {
        return new ModelAndView(ViewPrefixAttribute.POST_MANAGER_PRIFX);
    }

    @ResponseBody
    @RequestMapping(value = HttpAttribute.GET_POSTLIST_JSON)
    public Map<String, Object> getAllPost(
            @RequestParam(value = ParameterAttribute.SEARCH_NAME, required = false, defaultValue = "") String searchName,
            @RequestParam(value = ParameterAttribute.SEARCH_TYPE, required = false, defaultValue = "") String searchType,
            @RequestParam(value = ParameterAttribute.PAGE, required = false, defaultValue = "1") Integer page,
            @RequestParam(value = ParameterAttribute.ROWS, required = false, defaultValue = "10") Integer rows) throws Exception {
        Map<String, Object> returnMap = new HashedMap();
        try {
            Map<String, Object> querymap = new HashMap<String, Object>();
            if (!StringUtils.isEmpty(searchName)) {
                querymap = MapUtils.PutLikeMaps(querymap, searchName, searchType,
                        ParameterAttribute.CONTENT
                );
            }
            querymap.put(ParameterAttribute.PAGE_START, (page - 1) * rows);
            querymap.put(ParameterAttribute.PAGE_END, rows);
            returnMap = postService.getAllPost(querymap);
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }

}
