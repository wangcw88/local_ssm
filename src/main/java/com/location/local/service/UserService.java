package com.location.local.service;

import com.location.local.dao.UserDao;
import com.location.local.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService  {
    //public UserService(){}
    @Autowired
    UserDao userDao;
    public void saveInSQL(double[] D){

        User user;
        //user.setUsername("wang");
        user=userDao.selectByUsername("wangcw");
        if(user.getUsername().equals("wangcw")){
//            user.setLng("111");
//            user.setLat("222");
            user.setLng(String.valueOf(D[0]));
            user.setLat(String.valueOf(D[1]));

            System.out.println(user);
            userDao.updateLocation(user);
            System.out.println("更新成功");
        }else{
            user.setUsername("wang");
//            user.setLng(String.valueOf(D[0]));
//            user.setLat(String.valueOf(D[1]));
            userDao.updateLocation(user);
            System.out.println("插入成功");
        }

    }
}
