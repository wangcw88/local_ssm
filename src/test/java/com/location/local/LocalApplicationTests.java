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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalApplicationTests {
    @Resource
    UserDao userDao;

    @Resource
    LocationDao locationDao;


    @Test
    public void contextLoads() {



        //locationDao.updateLocation(location);
    }

}
