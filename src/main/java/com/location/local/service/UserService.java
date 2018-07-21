package com.location.local.service;

import com.location.local.dao.UserDao;
import com.location.local.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class UserService {
    //public UserService(){}
    @Resource
    private UserDao userDao;


    public void saveInSQL(double[] D, String username){


        User user = userDao.selectByUsername(username);
        if(user.getUsername().equals("qwer")){
            user.setLng("111");
            user.setLat("222");
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
    public void aaa(double[] D){

        User user=userDao.selectByUsername("wcw");
        user.setLng(String.valueOf(D[0]));
        user.setLat(String.valueOf(D[1]));
        userDao.updateLocation(user);
    }

}