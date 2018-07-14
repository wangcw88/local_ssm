package com.location.local.server;

import com.location.local.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    UserDao userDao;
}
