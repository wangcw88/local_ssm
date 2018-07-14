package com.location.local;

import com.location.local.dao.UserDao;
import com.location.local.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Sql("/init-schema.sql")
public class DatabaseTests {
    @Autowired
    UserDao userDao;
    private static String usern = "wangcw";
    private static String password = "123456";

    @Test
    public void databaseTest(){


        if(true ) {
//            User user = new User();
//            user.setUsername("zxc");
//            user.setPassword("123");
//            userDao.addUser(user);
//            user.setLat("11");
//            user.setLng("22");
//            userDao.updateLocation(user);
            System.out.println("hello");
            userDao.deleteByUsername("zxc");
        }
        else{
            System.out.println("hi");
        }
    }
}
