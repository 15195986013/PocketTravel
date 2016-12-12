package com.njbd.pt.controller.System;

import com.njbd.pt.attribute.HttpAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping(value = HttpAttribute.ADMIN_HTML)
public class ResultServlet {


     @RequestMapping(value="resultServlet/validateCode",method= RequestMethod.POST)
     public void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         response.setContentType("text/html;charset=utf-8");        
         String validateC = (String) request.getSession().getAttribute("validateCode");
         String veryCode = request.getParameter("c");
         PrintWriter out = response.getWriter();
         if(veryCode==null||"".equals(veryCode)){        
             out.println(0);
         }else{        
             if(validateC.equalsIgnoreCase(veryCode)){
                 out.println(1);
             }else{
                 out.println(2);
             }        
         }        
         out.flush();        
         out.close();        
     }        
}