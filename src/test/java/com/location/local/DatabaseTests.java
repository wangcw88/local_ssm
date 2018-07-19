package com.location.local;


import com.location.local.dao.UserDao;
import com.location.local.model.User;
import com.location.local.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Sql("/init-schema.sql")
public class DatabaseTests {
    @Resource
    UserDao userDao;
    private static String usern = "wangcw";
    private static String password = "123456";

    @Test
    public void databaseTest(){


        User user=new UserService().selectByUsername("wangcw");
        if(user.getUsername().equals("wangcw")){
            user.setLng("111");
            user.setLat("222");


            System.out.println(user);
            userDao.updateLocation(user);
            System.out.println("更新成功");
        }else{
            user.setUsername("wang");

            userDao.updateLocation(user);
            System.out.println("插入成功");
        }
        if(true ) {

            System.out.println("hello");
            userDao.deleteByUsername("zxc");
        }
        else{
            System.out.println("hi");
        }
    }
}
