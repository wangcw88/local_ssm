package com.location.local.server;

import com.location.local.dao.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;

public class LocationService {
    @Autowired
    LocationDao locationDao;
}
