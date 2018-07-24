package com.location.local.controller;

import com.location.local.SocketServer;
import com.location.local.dao.ApDao;
import com.location.local.dao.LocationDao;
import com.location.local.dao.UserDao;
import com.location.local.model.Location;
import com.location.local.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LocationController {

    @Resource
    LocationDao locationDao;

    @Resource
    UserDao userDao;

    @Resource
    ApDao apDao;

    Map<String, String> json;
    byte[] jsonBytes;

    // 请求url地址映射，类似Struts的action-mapping-----请求注册
    @RequestMapping("/gpslocation")
    public  Object Gpslocation(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("username");
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            System.out.println("<<<<<<<<<<<"+  "username= " + username + "　ＧＥＴ　ＧＰＳ　ＭＳＧ　>>>>>>>>>>>");
            System.out.println(latitude);
            System.out.println(longitude);
            json = new HashMap<String, String>();
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(dateFormat.format( now ));

            //插入用户名和时间
            User user = userDao.selectByUsername(username);
            Location location = new Location();
            location.setDate_time(now);
            location.setUsername(username);
            //locationDao.addLocation(location );

            //更新用户的位置信息
            location.setGps_lat(latitude);
            location.setGps_lng(longitude);

            location.setWifi_lat(user.getLat());
            location.setWifi_lng(user.getLng());
            location.setLbs_lat("1");
            location.setLbs_lng("1");
            locationDao.addLocation(location);

            jsonBytes = json.toString().getBytes("utf-8");
            response.setContentLength(jsonBytes.length);
            response.getOutputStream().write(jsonBytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //请求url地址映射------用户定位
    @RequestMapping("/wifirequest")
    public  Object Wifirequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("username");

            User user = userDao.selectByUsername(username);

            System.out.println("<<<<<<<<<<<  "+  "username= " + username + "  W O T K~!>>>>>>>>>>>");

            json = new HashMap<String, String>();
            if (user.getLat() != null && user.getLng() != null) {
                json.put("lng", user.getLng());
                json.put("lat", user.getLat());
                json.put("gps", user.getGps());
                json.put("wifi", user.getWifi());
            }


            jsonBytes = json.toString().getBytes("utf-8");
            response.setContentLength(jsonBytes.length);
            response.getOutputStream().write(jsonBytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();



        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }


}





















