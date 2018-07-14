package com.location.local.model;

import java.util.Date;

public class Location {
    private String wifi_lng;
    private String wifi_lat;
    private String lbs_lng;
    private String lbs_lat;
    private String gps_lng;
    private String gps_lat;
    private Long id;
    private String username;
    private Date created_time;

    public Date getDate_time() { return created_time; }

    public void setDate_time(Date date_time) { this.created_time = date_time; }

    public String getGps_lat() { return gps_lat; }

    public String getGps_lng() { return gps_lng; }

    public String getLbs_lat() { return lbs_lat; }

    public String getLbs_lng() { return lbs_lng; }

    public String getWifi_lat() { return wifi_lat; }

    public String getWifi_lng() { return wifi_lng; }

    public String getUsername() { return username; }

    public void setLbs_lng(String lbs_lng) { this.lbs_lng = lbs_lng; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public void setGps_lat(String gps_lat) { this.gps_lat = gps_lat; }

    public void setGps_lng(String gps_lng) { this.gps_lng = gps_lng; }

    public void setLbs_lat(String lbs_lat) { this.lbs_lat = lbs_lat; }

    public void setUsername(String username) { this.username = username; }

    public void setWifi_lat(String wifi_lat) { this.wifi_lat = wifi_lat; }

    public void setWifi_lng(String wifi_lng) { this.wifi_lng = wifi_lng; }
}

