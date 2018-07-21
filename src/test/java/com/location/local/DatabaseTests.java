package com.location.local;


import com.location.local.dao.LocationDao;
import com.location.local.dao.UserDao;
import com.location.local.model.Location;
import com.location.local.model.User;
import com.location.local.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Sql("/init-schema.sql")
public class DatabaseTests {
    @Resource
    UserDao userDao;
    @Test
    public void test(){
        User user=userDao.selectByUsername("wcw");
        user.setLng("1212");
        user.setLat("222");
        userDao.updateLocation(user);
    }


}
