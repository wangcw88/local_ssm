package com.location.local.service;

import com.location.local.dao.UserDao;
import com.location.local.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Controller
public class UserService {
    //public UserService(){}
    @Resource
    private UserDao userDao;

    public User selectByUsername(String username) {
        return userDao.selectByUsername(username);
    }

    public void saveInSQL(double[] D){
        UserService userService = new UserService();

        User user = userService.selectByUsername("qwer");

        if(user.getUsername().equals("qwer")){
//            user.setLng("111");
//            user.setLat("222");
            user.setLng(String.valueOf(D[0]));
            user.setLat(String.valueOf(D[1]));

            System.out.println(user);
            userDao.updateLocation(user);
            System.out.println("更新成功");
        }else{
            user.setUsername("qwer");
//            user.setLng(String.valueOf(D[0]));
//            user.setLat(String.valueOf(D[1]));
            userDao.updateLocation(user);
            System.out.println("插入成功");
        }

    }

}