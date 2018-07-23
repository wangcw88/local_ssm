package com.location.local.controller;


import com.location.local.dao.ApDao;
import com.location.local.model.Ap;
import com.location.local.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ApController {

    @Resource
    ApDao apDao;

    Map<String, String> json;
    byte[] jsonBytes;

    //请求url地址映射------有效热点定位
    @RequestMapping("/aprequest")
    public Object Aprequest(HttpServletRequest request, HttpServletResponse response){
        try {
            String username = request.getParameter("username");
            Ap ap = apDao.selectByUsername(username);

            json = new HashMap<String, String>();
            if (ap.getAlat() != null && ap.getAlng() != null &&
                    ap.getBlat() != null && ap.getBlng() != null &&
                    ap.getClat() != null && ap.getClng() != null ) {
                json.put("Alng", ap.getAlng());
                json.put("Alat", ap.getAlat());
                json.put("Blng", ap.getBlng());
                json.put("Blat", ap.getBlat());
                json.put("Clng", ap.getClng());
                json.put("Clat", ap.getClat());
                json.put("Lbslng",ap.getLbslng());
                json.put("Lbslat",ap.getLbslat());
            }

            jsonBytes = json.toString().getBytes("utf-8");
            response.setContentLength(jsonBytes.length);
            response.getOutputStream().write(jsonBytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();

            System.out.println("<<<<<<<<<<< "+  "username=  " + username + "AP MSG>>>>>>>>>>>");

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }
}
