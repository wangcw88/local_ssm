package com.location.local.service;

import com.location.local.dao.ApDao;
import com.location.local.dao.LocationDao;
import com.location.local.model.Ap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LocationService {
    @Resource
    LocationDao locationDao;
    @Resource
    ApDao apDao;

    public Ap selectByUsername(String username){return apDao.selectByUsername(username);}

    public void saveInDb(){

    }
}
