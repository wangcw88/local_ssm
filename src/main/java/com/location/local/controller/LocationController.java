package com.location.local.controller;

import com.location.local.SocketServer;
import com.location.local.dao.LocationDao;
import com.location.local.dao.UserDao;
import com.location.local.model.Location;
import com.location.local.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LocationController {

    @Autowired
    LocationDao locationDao;

    @Autowired
    UserDao userDao;

    Map<String, String> json;
    byte[] jsonBytes;


    // 请求url地址映射，类似Struts的action-mapping-----请求注册
    @RequestMapping("/gpslocation")
    public  Object Gpslocation(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("username");
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            System.out.println("<<<<<<<<<<<　ＧＥＴ　ＧＰＳ　ＭＳＧ　>>>>>>>>>>>");
            json = new HashMap<String, String>();
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(dateFormat.format( now ));

            //插入用户名和时间
            Location location = new Location();
            location.setDate_time(now);
            location.setUsername(username);
            locationDao.addLocation(location );

            //更新用户的位置信息
            location.setGps_lat(latitude);
            location.setGps_lng(longitude);
            location.setWifi_lat(SocketServer.wifi_lat);
            location.setWifi_lng(SocketServer.wifi_lon);
            location.setLbs_lat("1");
            location.setLbs_lng("1");
            locationDao.updateLocation(location);

            //更新用户信息
            User user = userDao.selectByUsername(username);
            user.setLng(SocketServer.wifi_lon);
            user.setLat(SocketServer.wifi_lon);
            user.setGps("0");
            user.setLbs("0");
            user.setWifi("0");
            userDao.updateLocation(user);


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

            // System.out.println("<<<<<<<<<<<W O T K~!>>>>>>>>>>>");

            json = new HashMap<String, String>();
            if (user.getLat() != null && user.getLng() != null) {
                json.put("lng", user.getLng());
                json.put("lat", user.getLat());
            }


            jsonBytes = json.toString().getBytes("utf-8");
            response.setContentLength(jsonBytes.length);
            response.getOutputStream().write(jsonBytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();

            //显示时间
//            Date now = new Date();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
//
//            System.out.println("lng = "+u.getLng()+" lat = "+u.getLat()+" time = "+dateFormat.format( now ));

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
