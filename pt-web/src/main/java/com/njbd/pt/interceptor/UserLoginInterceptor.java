package com.njbd.pt.interceptor;


import com.njbd.pt.attribute.ParameterAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by 李建成
 * ON:  2016/9/21 10:40
 */
public class UserLoginInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(UserLoginInterceptor.class);


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求的URL
        String url = request.getRequestURI();
        // logger.info("当前访问地址"+url);
        //URL:login.html是公开的;这个demo是除了login.jsp是可以公开访问的，其它的URL都进行拦截控制
        if (url.indexOf("login.html") >= 0) {
            return true;
        }
        HttpSession session = request.getSession();
        String account = (String) session.getAttribute(ParameterAttribute.ACCOUNT);
        if (account != null) {
            return true;
        }
        request.getRequestDispatcher("login.html").forward(request, response);
        return false;
    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
